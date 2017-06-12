using UnityEngine;
using System.Collections;

public class StartMenu : MonoBehaviour {

    public GUIStyle style;

    void OnGUI()
    {
        GUI.Label(new Rect(50, Screen.height / 4 - 75, 75, 25), "Controls: ", style);
        GUI.Label(new Rect(50, Screen.height / 4 - 25, 200, 100), "WASD To Move, Space to Jump, Left Click to Shoot, Right Click to Change Weapons, Middle Mouse to Activate Gravity Mode (Use WASD to change gravity Direction), P to pause", style);

        if (GUI.Button(new Rect(Screen.width / 2 - 100, Screen.height / 3f, Screen.width / 4, Screen.height / 20), "Arena"))
        {
            Utilities.loadHordeMode();
        }
        if (GUI.Button(new Rect(Screen.width / 2 - 100, Screen.height / 2.5f, Screen.width / 4, Screen.height / 20), "Single Player"))
        {
            Utilities.loadSinglePlayer();
        }
        if (GUI.Button(new Rect(Screen.width / 2 - 100, Screen.height / 2f, Screen.width / 4, Screen.height / 20), "Quit"))
        {
            //print ("Clicked End Game");
            Application.Quit();
        }
    }
}
