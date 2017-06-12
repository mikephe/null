using UnityEngine;
using System.Collections;
using System.Collections.Generic;

public class GameManager : MonoBehaviour {
    
	public int tempSpawnedDifficulty;
	public bool isVictorious = false;
	public GameObject instantiatedPlayer;
	public GameObject playerPrefab;
	public GameObject[] enemyType;
	public GameObject startPos;
	//public List<GameObject> enemyTypeList = new List<GameObject>();
	public List<GameObject> enemySpawnLocations = new List<GameObject> ();
	private List<GameObject> levelEnemyList = new List<GameObject>();
    private bool levelEnd = false;
    private bool pause = false;

	// Use this for initialization
	void Start () {
        
		instantiatedPlayer = GameObject.FindGameObjectWithTag ("Player");
        if (Utilities.hordeMode)
        {
            tempSpawnedDifficulty = 0;
            LevelBegin(Utilities.difficulty);
        }
	}

	void LevelBegin(int currLevel) {
		tempSpawnedDifficulty = 0;
		Utilities.enemyCount = 0;
		int tempDiff;
		GameObject tempEnemy;

		while (tempSpawnedDifficulty < Utilities.difficulty) {
			//tempDiff = Random.Range (minEnemyDifficulty, maxEnemyDifficulty);
			//create enemy of that diff
			int i = Random.Range(0, enemyType.Length);
			int s = Random.Range(0, enemySpawnLocations.Count);
			int enemyLevel = enemyType[i].GetComponent<Enemy>().level;
			if (enemyLevel <= Utilities.maxEnemyDifficulty && ((tempSpawnedDifficulty + enemyLevel) <= Utilities.difficulty)) {
			tempSpawnedDifficulty += enemyLevel;
			tempEnemy = (GameObject) Instantiate (enemyType[i], enemySpawnLocations[s].transform.position, Quaternion.identity);
			levelEnemyList.Add (tempEnemy);
			Utilities.enemyCount++;
			tempSpawnedDifficulty++;
			}

		}
	}

	void DestroyAllEnemies() {
		foreach (GameObject enemiesLeft in levelEnemyList) {
			if (!enemiesLeft.Equals(null)) {
				GameObject.Destroy(enemiesLeft);
			}
		}
		levelEnemyList.Clear();
	}

	void LevelEnd(bool victoryCondition) {
		if (victoryCondition) {
			// do Victory Stuff:
			//    1) Victory Screen 
			//    2) Click to Load new level
			//    3) LevelsSurvived++
			DestroyAllEnemies();
			Utilities.currentHealth = instantiatedPlayer.GetComponent<Player>().health;
            Utilities.playerWeaponIndex = instantiatedPlayer.GetComponent<Player>().getWeaponIndex();
            Utilities.playerState = instantiatedPlayer.GetComponent<Player>().state;

            Time.timeScale = 1;

		} else {
			// do Lose Stuff:
			//    1) Lose Screen
			//    2) Print LevelsSurvived
			//    3) Options 
			//       a) Main Menu
			//       b) Restart
			DestroyAllEnemies();
            levelEnd = true;
			Utilities.difficulty = 5;
			Utilities.currentHealth = playerPrefab.GetComponent<Player>().maxHealth;
			Utilities.currLevel = 1;
			Utilities.minEnemyDifficulty = 1;
			Utilities.maxEnemyDifficulty = 1;
			Utilities.minSpawn = 1;
			Utilities.maxSpawn = 100;
			Time.timeScale = 1;
		}
	}
	
	// Update is called once per frame
	void Update () {
        //Debug.Log (Utilities.maxEnemyDifficulty);
        //Debug.Log (instantiatedPlayer);
        if (Input.GetKeyDown(KeyCode.P))
        {
            pause = true;
        }
        if (Utilities.hordeMode)
            hordeUpdate();
        if (Utilities.singlePlayer)
            singlePlayerUpdate();
        Utilities.gravityUpdate();
	}

    void singlePlayerUpdate()
    {
        if (instantiatedPlayer.Equals(null))
        {
            //Debug.Log("WUHT!?");
            isVictorious = false;
            LevelEnd(isVictorious);
        }
    }

    void hordeUpdate()
    {
        if (instantiatedPlayer.Equals(null))
        {
            //Debug.Log("WUHT!?");
            isVictorious = false;
            LevelEnd(isVictorious);
        }
        //Debug.Log("EnemyCount: " + Utilities.enemyCount);
        if (Utilities.enemyCount <= 0)
        {
            isVictorious = true;
            LevelEnd(isVictorious);
        }
        if (instantiatedPlayer.Equals(null)
            && Input.GetKey(KeyCode.N))
        {
            Time.timeScale = 1;
            Utilities.difficulty = 10;
            instantiatedPlayer = (GameObject)Instantiate(playerPrefab,
                                                            startPos.transform.position,
                                                             Quaternion.identity);
            LevelBegin(Utilities.difficulty);
        }
    }

	//text for the score
	void OnGUI() {
		if (isVictorious) {
			if (GUI.Button (new Rect(Screen.width/2 - 100, Screen.height/2, Screen.width/4, Screen.height/20),"End Game")) {
				//print ("Clicked End Game");
				Application.Quit();
			}
			if (GUI.Button (new Rect(Screen.width/2 - 100, Screen.height/2.5f, Screen.width/4, Screen.height/20),"Next Level")) {
				//print ("Clicked End Game");
				Time.timeScale = 1;
				Utilities.difficulty += 5;
				Utilities.currLevel++;
				Utilities.maxEnemyDifficulty += 5;
				Application.LoadLevel(Application.loadedLevel);
			}
            if (GUI.Button(new Rect(Screen.width / 2 - 100, Screen.height / 3f, Screen.width / 4, Screen.height / 20), "Main Menu"))
            {
                Utilities.loadMainMenu();
                Time.timeScale = 1;
            }
        }
        if (levelEnd)
        {
            if (GUI.Button(new Rect(Screen.width / 2 - 100, Screen.height / 1.5f, Screen.width / 4, Screen.height / 20), "Quit"))
            {
                //print ("Clicked End Game");
                Application.Quit();
            }
            if (GUI.Button(new Rect(Screen.width / 2 - 100, Screen.height / 2, Screen.width / 4, Screen.height / 20), "End Game"))
            {
                //print ("Clicked End Game");
                Utilities.loadEnd(); ;
            }
            if (GUI.Button(new Rect(Screen.width / 2 - 100, Screen.height / 2.5f, Screen.width / 4, Screen.height / 20), "Restart"))
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
                Application.LoadLevel(Application.loadedLevel);
            }
            if (GUI.Button(new Rect(Screen.width / 2 - 100, Screen.height / 3f, Screen.width / 4, Screen.height / 20), "Main Menu"))
            {
                Utilities.loadMainMenu();
                Time.timeScale = 1;
            }
        }
        if (pause)
        {
            Time.timeScale = 0;
            if (GUI.Button(new Rect(Screen.width / 2 - 100, Screen.height / 1.5f, Screen.width / 4, Screen.height / 20), "Quit"))
            {
                //print ("Clicked End Game");
                pause = false;
                Application.Quit();
            }
            if (GUI.Button(new Rect(Screen.width / 2 - 100, Screen.height / 2, Screen.width / 4, Screen.height / 20), "End Game"))
            {
                //print ("Clicked End Game");
                pause = false;
                Utilities.loadEnd();
            }
            if (GUI.Button(new Rect(Screen.width / 2 - 100, Screen.height / 2.5f, Screen.width / 4, Screen.height / 20), "Restart"))
            {
                //print ("Clicked End Game");
                pause = false;
                Time.timeScale = 1;
                Utilities.difficulty = 5;
                Utilities.currentHealth = 100;
                Utilities.currLevel = 1;
                Utilities.minEnemyDifficulty = 1;
                Utilities.maxEnemyDifficulty = 1;
                Utilities.minSpawn = 1;
                Utilities.maxSpawn = 100;
                Utilities.score = 0;
                Application.LoadLevel(Application.loadedLevel);
            }
            if (GUI.Button(new Rect(Screen.width / 2 - 100, Screen.height / 3, Screen.width / 4, Screen.height / 20), "Resume") || Input.GetKeyDown(KeyCode.P))
            {
                pause = false;
                Time.timeScale = 1;
            }
            if (GUI.Button(new Rect(Screen.width / 2 - 100, Screen.height / 3.5f, Screen.width / 4, Screen.height / 20), "Main Menu"))
            {
                pause = false;
                Utilities.loadMainMenu(); ;
                Time.timeScale = 1;
            }
        }
	}
}
