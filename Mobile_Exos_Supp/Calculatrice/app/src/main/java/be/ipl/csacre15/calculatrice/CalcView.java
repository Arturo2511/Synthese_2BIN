package be.ipl.csacre15.calculatrice;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import be.ipl.csacre15.calculatrice.CalcModel;
import be.ipl.csacre15.calculatrice.R;

/**
 * Created by csacre15 on 15/03/2017.
 */
public class CalcView extends FragmentActivity implements CalcModel.CalcModelChangeObserver, MyFrag.ArithmeticObserver {

    private CalcModel model;
    private TextView displayTextView;
    private View fragmentView;
    private static final int RED = 0;
    private static final int GREEN = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        displayTextView = (TextView) findViewById(R.id.screen);
        //Model initialisation
        model = ((Builder) getApplication()).getModel();
        model.registerObserver(this);
        //Digit's Button registration
        int[]digitIds = {R.id.button1, R.id.button2,
                R.id.button3, R.id.button4, R.id.button5,
                R.id.button6, R.id.button7, R.id.button8,
                R.id.button9, R.id.button0};
        for (int id : digitIds) {
            Button button = (Button) findViewById(id);
            button.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    model.addDigit(((Button) view).getTag().toString().charAt(0));
                }
            });
        }
        //Operators Button registration
        int[] operatorIds = {R.id.addition, R.id.soustraction, R.id.division, R.id.multiplication};
        for(int id : operatorIds) {
            Button button = (Button) findViewById(id);
            button.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    model.addOperator(((Button) view).getTag().toString().charAt(0));
                }
            });
        }
        int[] operationsIds = {R.id.effacerTout, R.id.effacer, R.id.inversion, R.id.suppression, R.id.egal, R.id.virgule};
        for (int id : operationsIds) {
            Button button = (Button) findViewById(id);
            button.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    model.operation(((Button) view).getTag().toString().charAt(0));
                }
            });
        }
        fragmentView =  findViewById(R.id.fragment_container);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(model.isArtihmeticException()) {
            changeColor(RED);
        } else {
            changeColor(GREEN);
        }
    }

    private void changeColor(int color) {
        if (color == RED) {
            fragmentView.setBackgroundColor(getResources().getColor(R.color.colorRed));
            fragmentView.findViewById(R.id.fragment_button).setBackgroundColor(getResources().getColor(R.color.colorRed));
        } else {
            fragmentView.setBackgroundColor(getResources().getColor(R.color.colorGreen));
            fragmentView.findViewById(R.id.fragment_button).setBackgroundColor(getResources().getColor(R.color.colorGreen));
        }

    }

    @Override
    public void notifyChange() {
        String display;
        if(model.isOperandLeftDisplay()){
            display = model.getOperandLeft();
        } else {
            display = model.getOperandRight();
        }
        displayTextView.setText(display);
    }

    @Override
    public void notifyArithmeticException() {
        changeColor(RED);
    }

    @Override
    public void notifyClick() {
        changeColor(GREEN);
    }
}