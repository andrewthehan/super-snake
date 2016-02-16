class C{
	public static final short WINDOW_WIDTH=600, WINDOW_HEIGHT=600,
							SMALL_WINDOW_WIDTH=400, SMALL_WINDOW_HEIGHT=200,

							B_WIDTH=300, B_HEIGHT=60,
							B_X=150,
							B1_Y=200,
							B2_Y=275,
							B3_Y=350,
							B4_Y=425,
							B5_Y=500,

							B_MUTE_WIDTH=32, B_MUTE_HEIGHT=30,
							B_MUTE_X=554,
							B_MUTE_Y=556,

							B_SMALL_WIDTH=120, B_SMALL_HEIGHT=40,

							B_NEXT_X=425,
							B_PREVIOUS_X=50,
							B_NEXT_PREVIOUS_Y=50,

							B_SMALL_LEFT_X=50,
							B_SMALL_RIGHT_X=225,
							B_SMALL_Y=140,

							B_OK_HS_X=140,
							B_OK_HS_Y=150,

							B_PAUSE_MAIN_MENU_X=240,
							B_PAUSE_MAIN_MENU_Y=375,

							NAME_WIDTH=100, NAME_HEIGHT=20,
							NAME_X=150, NAME_Y=121,

							SIZE=15,

							UP_BORDER=15, RIGHT_BORDER=585, DOWN_BORDER=585, LEFT_BORDER=15,

							DEMO_UP_BORDER=30, DEMO_RIGHT_BORDER=570, DEMO_DOWN_BORDER=180, DEMO_LEFT_BORDER=30,

							HIGH_SCORE_START_Y=108,
							HIGH_SCORE_NEXT_Y=38,
							HIGH_SCORE_NAME_X=140,
							HIGH_SCORE_SCORE_X=400;

	/*random integer number generator
	 *@param two integer numbers that represent the minumum value and maximum value respectively
	 *@return an integer number between the two
	 */
	public static short random(short min, short max){
		return (short)(Math.floor(Math.random()*(max-min+1))+min);
	}

	/*random coordinate generator
	 *@param none
	 *@return a random coordinate coor: [15, 570] (length +- borders and size)
	 */
	public static short randomCoor(){
		short coor;
		do{
			coor=random(SIZE, DEMO_RIGHT_BORDER);
		}while(coor%15!=0);
		return coor;
	}

	/*random x coordinate for demo generator
	 *@param none
	 *@return a random x coordinate for demo coor: [15, 165] (length +- borders and size)
	 */
	public static short xDemoCoor(){
		short coor;
		do{
			coor=random(DEMO_LEFT_BORDER, (short)(DEMO_RIGHT_BORDER-SIZE));
		}while(coor%15!=0);
		return coor;
	}

	/*random y coordinate for demo generator
	 *@param none
	 *@return a random y coordinate for demo coor: [15, 555] (length +- borders and size)
	 */
	public static short yDemoCoor(){
		short coor;
		do{
			coor=random(DEMO_UP_BORDER, (short)(DEMO_DOWN_BORDER-SIZE));
		}while(coor%15!=0);
		return coor;
	}
}