using UnityEngine;
using System.Collections;

public class Text : MonoBehaviour {
	public GUIStyle style;

	void OnGUI() {
		GUI.Label (new Rect (Screen.width/2 - 100, Screen.height/2 - 75, 100, 100), "SCORE: " + Utilities.score, style);
        if (GUI.Button(new Rect(Screen.width / 2 - 100, Screen.height / 2.5f, Screen.width / 4, Screen.height / 20), "Main Menu"))
        {
            Utilities.loadMainMenu();
            Time.timeScale = 1;
        }
        if (GUI.Button(new Rect(Screen.width / 2 - 100, Screen.height / 2f, Screen.width / 4, Screen.height / 20), "Retry"))
        {
            //print ("Clicked End Game");
            Time.timeScale = 1;
            Utilities.difficulty = 5;
            Utilities.currentHealth = 100;
            Utilities.currLevel = 1;
            Utilities.minEnemyDifficulty = 1;
            Utilities.maxEnemyDifficulty = 1;
            Utilities.minSpawn = 1;
            Utilities.maxSpawn = 100;
            Utilities.score = 0;
            Utilities.loadHordeMode();
        }
        if (GUI.Button(new Rect(Screen.width / 2 - 100, Screen.height / 1.5f, Screen.width / 4, Screen.height / 20), "Quit"))
        {
            //print ("Clicked End Game");
            Application.Quit();
        }
    }
}
