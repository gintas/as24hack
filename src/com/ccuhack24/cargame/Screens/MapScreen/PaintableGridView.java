package com.ccuhack24.cargame.Screens.MapScreen;

import java.util.ArrayList;

import android.content.Context;
import android.content.res.Resources.Theme;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

public class PaintableGridView extends View {

    // transparency of all our colors
    private final int teamPaintAlpha = 120;
    public ArrayList<Paint> teamPaints;

    // dimensions of the a single cell in grid
    private float cellWidth;
    private float cellHeight;

    // the actual playing field with all the team IDs in each cell
    private int[][] teamField;

    public PaintableGridView(Context context, int screenWidth,
	    int screenHeight, int[][] field) {
	super(context);
	float numberOfCellsX = field.length;
	float numberOfCellsY = field[0].length;
	cellWidth = screenWidth / numberOfCellsX;
	cellHeight = screenHeight / numberOfCellsY;

	teamField = field;

	// initialize the different team Paints, so we can use them in the drawing process
	initPaints();

    }

    @Override
    protected void onDraw(Canvas canvas) {
	for (int i = 0; i < teamField.length; i++)
	    for (int j = 0; j < teamField[0].length; j++) {
		// get the correct color for the current field based on the team ID
		int currentTeam = teamField[i][j];
		Paint currentPaint = teamPaints.get(currentTeam);

		// calculate the position of the current field
		float currentX = i * cellWidth;
		float currentY = j * cellHeight;

		// now draw the colored rectangle
		canvas.drawRect(currentX, currentY, currentX + cellWidth,
			currentY + cellHeight, currentPaint);

	    }
	super.onDraw(canvas);
    }

    private void initPaints() {
	// we have 5 different team colors, all use the same transparency
	// blue
	// red
	// green
	// yellow
	// cyan
	teamPaints = new ArrayList<Paint>();
	Paint tmpPaint = new Paint();
	tmpPaint.setColor(Color.BLUE);
	tmpPaint.setAlpha(teamPaintAlpha);
	teamPaints.add(tmpPaint);

	tmpPaint = new Paint();
	tmpPaint.setColor(Color.RED);
	tmpPaint.setAlpha(teamPaintAlpha);
	teamPaints.add(tmpPaint);

	tmpPaint = new Paint();
	tmpPaint.setColor(Color.GREEN);
	tmpPaint.setAlpha(teamPaintAlpha);
	teamPaints.add(tmpPaint);

	tmpPaint = new Paint();
	tmpPaint.setColor(Color.YELLOW);
	tmpPaint.setAlpha(teamPaintAlpha);
	teamPaints.add(tmpPaint);

	tmpPaint = new Paint();
	tmpPaint.setColor(Color.CYAN);
	tmpPaint.setAlpha(teamPaintAlpha);
	teamPaints.add(tmpPaint);
    }

}
