package nst.nullspaceterminal.signal;

//Java std lib imports
import java.lang.String;
import java.lang.Integer;
import java.lang.reflect.Array;
import nst.nullspaceterminal.tictactoe.R;

//debugging purposes
import android.util.Log;

import android.app.Activity;
import android.os.Bundle;

import android.view.View;

import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class TicTacToeActivity extends Activity
{
    //It's easier this way. Sorry memory you're taking the back seat this time
    private String[] board = new String[9];
    private int current_turn = 0;

    //For logging/debug purposes
    private static final String TAG = "MeinActivity";

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        initiateBoard();

    }

    @Override
    public void onPause() {
    	super.onPause();
    }

    @Override 
    public void onResume() {
    	super.onResume();
    }

    public void makeMove(View view) {
        /*Casting required to access setText method of button*/
        Button current_button = (Button) view; 

        String current_player = "";

        if (current_turn % 2 == 0)
            current_player = "X";
        else
            current_player = "O";

        current_button.setText(current_player);
        current_turn++;
        current_button.setEnabled(false);

        //Need to find out where it is on the grid
        LinearLayout parent_view = (LinearLayout) current_button.getParent();
        int board_position = parent_view.indexOfChild(current_button);

        //adjust the index to match the index of the char array
        switch(parent_view.getId()) {
            case R.id.first_row:
                break;
            case R.id.second_row:
                board_position += 3;
                break;
            case R.id.third_row:
                board_position += 6;
                break;
        }

        board[board_position] = current_player;

        boolean winner = checkWinner(board);

        //TODO Append a play again button?
        if (winner) {
            Toast toast = Toast.makeText(this, (current_player + " Won!"), Toast.LENGTH_SHORT);
            toast.show();
        }
            
        //TODO remove these calls
        //Log.v(TAG, ArrayToString(board));
        //Log.v(TAG, String.valueOf(board_position));
    }

    private boolean checkWinner(String[] board) {
        boolean winner = false;
        //Initial Check
        //Horizontal Row
        if (
                //Rows
                ((board[0] == board[1]) && (board[1] == board[2])) ||
                ((board[3] == board[4]) && (board[4] == board[5])) ||
                ((board[6] == board[7]) && (board[7] == board[8])) ||
                //Column
                ((board[0] == board[3]) && (board[3] == board[6])) ||
                ((board[1] == board[4]) && (board[4] == board[7])) ||
                ((board[2] == board[5]) && (board[5] == board[8])) ||
                //Diagonal
                ((board[0] == board[4]) && (board[4] == board[8])) ||
                ((board[2] == board[4]) && (board[4] == board[6])) 
            ) 
            winner = true;
        return winner;
    }

    /**
     * Create a new game
     * @param view
     */
    public void initiateNewGame(View view) {
    	current_turn = 0;
    	initiateBoard();
    	LinearLayout layout_root = (LinearLayout) findViewById(R.id.root);
    	
    	for (int i = 0; i < 3; i++) {
    		LinearLayout current_row = (LinearLayout) layout_root.getChildAt(i);
    		for (int j = 0; j < 3; j++) {
    			Button current_button = (Button) current_row.getChildAt(j);
    			current_button.setText(" ");
    			current_button.setEnabled(true);
    		}
    	}
    }
    
    private void initiateBoard() {
        /*Initialize because I never trust auto initialization*/
        for (int i=0; i<9; i++) {
            this.board[i] = String.valueOf(i);
        }    	
    }
    
    /**
     * A function solely used for debugging to check the contents of the array
     * that is reflecting the board
     * TODO remove this once done
     */
    private String ArrayToString(String[] board) {
        String representation = "";
        int row_limiter = 0;
        for (int i = 0; i < (Array.getLength(board)); i++) {
            if (row_limiter % 3 == 0)
                representation += "\n";
            representation += board[i] + " ";
            row_limiter++; 
        }

        return representation;
    }
    
}
