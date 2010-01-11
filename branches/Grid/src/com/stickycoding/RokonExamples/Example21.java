/**
 * @author Jeremy
 * Grid Control
 * Grid has 3 level
 * Grid
 *  |
 *  - Container
 *       |
 *       - Sprite
 *       
 * Grid class :
 * 
 * Grid(Cols Width, Rows Height, Number of Cols, Number of Rows[, Pos X, Pos Y])
 *   
 * Grid allow you to stuck some Sprites on them like a sprites layer and to position them in a grid
 *       
 * Container class :
 * 
 * Container is an ArrayList automatically created when you create à grid,
 * it contain sprites with their coordinates in the grid (Colon and Row)
 * it's useful if you want to know what sprites are in the grid and their location
 * they are sorted by sprites hashCodes
 * 
 * getSprite() : return Sprite
 * getCol()	: return Col of the grid
 * getRow() : return Row of the grid
 * 
 * 
 */
package com.stickycoding.RokonExamples;

import com.stickycoding.Rokon.RokonActivity;
import com.stickycoding.Rokon.Sprite;
import com.stickycoding.Rokon.Texture;
import com.stickycoding.Rokon.TextureAtlas;
import com.stickycoding.Rokon.TextureManager;
import com.stickycoding.Rokon.Backgrounds.FixedBackground;
import com.stickycoding.Rokon.Grid;
import com.stickycoding.Rokon.Debug;

public class Example21 extends RokonActivity {
	
	public TextureAtlas atlas;
	public FixedBackground background;
	
	public Texture [] narutoTexture = new Texture[2];
	public Sprite [] narutoSprite = new Sprite[10];

	public int x = 150, y = 150, sensX = 1, sensY = 1;
	public Grid testGrid1, testGrid2; 

    public void onCreate() {
    	createEngine("graphics/loading.png", 480, 320, true);
    }

	@Override
	public void onLoad() {
		// Creation of grids
		// Grid = new Grid(Cols Width, Rows Height, Number of Cols, Number of Rows(, Pos X, Pos Y))
		testGrid1 = new Grid(40,40,3,3,x,y);
		testGrid2 = new Grid(40,40,3,3);
		//
		atlas = new TextureAtlas(512, 1024);
		atlas.insert(narutoTexture[0] = new Texture("graphics/sprites/naruto.png"));
		atlas.insert(narutoTexture[1] = new Texture("graphics/backgrounds/beach.png"));
		narutoTexture[0].setTileSize(40, 40);
		TextureManager.load(atlas);

		narutoSprite[0] = new Sprite(100, 100, 40, 40, narutoTexture[0]);
    	narutoSprite[1] = new Sprite(140, 100, 40, 40, narutoTexture[0]);
    	narutoSprite[2] = new Sprite(100, 140, 40, 40, narutoTexture[0]);
    	narutoSprite[3] = new Sprite(140, 140, 40, 40, narutoTexture[0]);
    	narutoSprite[4] = new Sprite(180, 140, 40, 40, narutoTexture[0]);
        rokon.addSprite(narutoSprite[0]);
        rokon.addSprite(narutoSprite[1]);
        rokon.addSprite(narutoSprite[2]);
        rokon.addSprite(narutoSprite[3]);
        rokon.addSprite(narutoSprite[4]);

        narutoSprite[5] = new Sprite(40, 80, 40, 40, narutoTexture[0]);
    	narutoSprite[6] = new Sprite(80, 80, 40, 40, narutoTexture[0]);
    	narutoSprite[7] = new Sprite(120, 80, 40, 40, narutoTexture[0]);
    	narutoSprite[8] = new Sprite(120, 120, 40, 40, narutoTexture[0]);
    	narutoSprite[9] = new Sprite(80, 120, 40, 40, narutoTexture[0]);
        rokon.addSprite(narutoSprite[5]);
        rokon.addSprite(narutoSprite[6]);
        rokon.addSprite(narutoSprite[7]);
        rokon.addSprite(narutoSprite[8]);
        rokon.addSprite(narutoSprite[9]);

        // magnetize sprite to the grid
        // Grid.magnetize(Sprite, Col, Row)
		testGrid1.magnetize(narutoSprite[0], 0, 0);
		testGrid1.magnetize(narutoSprite[1], 1, 0);
		testGrid1.magnetize(narutoSprite[2], 2, 0);
		testGrid1.magnetize(narutoSprite[3], 3, 0);
		testGrid1.magnetize(narutoSprite[4], 0, 1);

		testGrid2.magnetize(narutoSprite[5], 0, 0);
		testGrid2.magnetize(narutoSprite[6], 1, 0);
		testGrid2.magnetize(narutoSprite[7], 2, 0);
		testGrid2.magnetize(narutoSprite[8], 3, 0);
		testGrid2.magnetize(narutoSprite[9], 0, 1);
		//
        
        int animeNaruto0[] = {2,13,2,24};
        int animeNaruto1[] = {6,17};
        int animeNaruto2[] = {37,48,37,59};
        int animeNaruto3[] = {9,20,9,31};
        int animeNaruto4[] = {41,42,43,44};

        narutoSprite[0].animateCustom(animeNaruto3, 200);
        narutoSprite[1].animateCustom(animeNaruto4, 200);
        narutoSprite[2].animateCustom(animeNaruto0, 200);
        narutoSprite[3].animateCustom(animeNaruto1, 200);
        narutoSprite[4].animateCustom(animeNaruto2, 200);

        narutoSprite[5].animateCustom(animeNaruto3, 200);
        narutoSprite[6].animateCustom(animeNaruto4, 200);
        narutoSprite[7].animateCustom(animeNaruto0, 200);
        narutoSprite[8].animateCustom(animeNaruto1, 200);
        narutoSprite[9].animateCustom(animeNaruto2, 200);
        
        background = new FixedBackground(narutoTexture[1]);
	}

	@Override
	public void onLoadComplete() {
		rokon.setBackground(background);
	}

	@Override
	public void onGameLoop() {
		//
		// Example 1 : move the grid 1 with all the objects hung. 
		//
		// Grid.moveGrid(x, y)
		if (x > 250) { sensX = -1; } else if (testGrid1.getX() < 150) { sensX = 1; }
		if (y > 225) { sensY = -1; } else if (testGrid1.getY() < 150) { sensY = 1; }
		if (sensX > 0) { x += 2; } else { x -= 2; }
		if (sensY > 0) { y += 2; } else { y -= 2; }
		testGrid1.moveGrid(x, y);
		
		//
		// Example 2 : move sprites on the grid.
		//
		// Grid.moveOnGrid(Sprite, Col, Row);
		int i = 0, j = testGrid2.getContainer().size(), Col, Row;

		while (i < j) {
	    	Col = testGrid2.getSpriteCol(testGrid2.getContainer().get(i).getSprite());
	    	Row = testGrid2.getSpriteRow(testGrid2.getContainer().get(i).getSprite());

	    	if (testGrid2.getContainer().get(i).getCol() == 3) {
	    		Col = 0;
		    	if (testGrid2.getContainer().get(i).getRow() == 3) {
		    		Row = 0;
		    	} else {
		    		Row = testGrid2.getContainer().get(i).getRow() + 1;
		    	}
	    	} else {
	    		Col = testGrid2.getContainer().get(i).getCol() + 1;
	    	}
	    	// Grid.moveOnGrid(Sprite, Col, Row); 
	    	testGrid2.moveOnGrid(testGrid2.getContainer().get(i).getSprite(), Col, Row);
	    	i++;
	    }

		//
		// Example 3 : Checking a position in a grid.
		//
		//  Grid.getOnGridPosX(x)
		// 		return -1 if point is out off the grid
		//		return Colon of the grid
		//  Grid.getOnGridPosY(y)
		// 		return -1 if point is out off the grid
		//		return Row of the grid
		//  Grid.getOnGridPosXY(x,y)
		// 		return an array with col and row
	    Debug.print("=================================");
	    Debug.print("testGrid1 x "+testGrid1.getOnGridPosX(210));
	    Debug.print("testGrid1 y "+testGrid1.getOnGridPosY(210));
	    Debug.print("---------------------------------");
	    Debug.print("testGrid2 x "+testGrid2.getOnGridPosX(120));
	    Debug.print("testGrid2 y "+testGrid2.getOnGridPosY(120));

	}
	
	@Override
	public void onRestart() {
		super.onRestart();
		rokon.unpause();
	}
}