using UnityEngine;
using System.Collections;


public enum GravityDirection
{
    North, South, West, East
}

public enum EntityType
{
    Player, Enemy
}

public static class Utilities {
	public static float currentHealth = 100;
	public static int score;
	public static int enemyCount;
	public static int difficulty = 5;
	public static int maxEnemyDifficulty = 1;
	public static int minEnemyDifficulty = 1;
	public static int currLevel = 1;
	public static int maxSpawn = 25;
	public static int minSpawn = 1;
    public static GravityDirection gravityDir = GravityDirection.South;
    public static WeaponState playerState = WeaponState.Standard;
    public static int playerWeaponIndex = 0;
    public static bool hordeMode = false;
    public static bool singlePlayer = false;

    public static Transform getPlayerTransform() {
		if (GameObject.FindGameObjectWithTag ("Player") != null) {
			return GameObject.FindGameObjectWithTag ("Player").gameObject.transform;
		}
		return null;
	}

    public static void loadHordeMode()
    {
        Utilities.hordeMode = true;
        Utilities.singlePlayer = false;
        Application.LoadLevel(1);
    }

    public static void loadSinglePlayer()
    {
        Utilities.hordeMode = false;
        Utilities.singlePlayer = true;
        Application.LoadLevel(3);
    }

    public static void loadEnd()
    {
        Utilities.hordeMode = false;
        Utilities.singlePlayer = false;
        Application.LoadLevel(2);
    }

    public static void loadMainMenu()
    {
        Utilities.hordeMode = false;
        Utilities.singlePlayer = false;
        Application.LoadLevel(0);
    }

    public static Player getPlayer()
    {
        if (GameObject.FindGameObjectWithTag("Player") != null)
        {
            return GameObject.FindGameObjectWithTag("Player").GetComponent<Player>();
        }
        return null;
    }

    public static float getHealth()
    {
        if (GameObject.FindGameObjectWithTag("Player") != null)
        {
            return GameObject.FindGameObjectWithTag("Player").GetComponent<Player>().health;
        }
        return 0;
    }

    public static float getMaxHealth()
    {
        if (GameObject.FindGameObjectWithTag("Player") != null)
        {
            return GameObject.FindGameObjectWithTag("Player").GetComponent<Player>().maxHealth;
        }
        return 0;
    }

    public static void gravityUpdate()
    {
        if (Utilities.gravityDir.Equals(GravityDirection.East))
        {
            Physics2D.gravity = new Vector3(9.8f, 0, 0);
        }
        if (Utilities.gravityDir.Equals(GravityDirection.West))
        {
            Physics2D.gravity = new Vector3(-9.8f, 0, 0);
        }
        if (Utilities.gravityDir.Equals(GravityDirection.North))
        {
            Physics2D.gravity = new Vector3(0, 9.8f, 0);
        }
        if (Utilities.gravityDir.Equals(GravityDirection.South))
        {
            Physics2D.gravity = new Vector3(0, -9.8f, 0);
        }
    }


    public static float calculateMouseAngle(Transform transform)
    {
        Vector3 mousePos = Input.mousePosition;

        Vector3 objectPos = Camera.main.WorldToScreenPoint(transform.position);
        mousePos.x = mousePos.x - objectPos.x;
        mousePos.y = mousePos.y - objectPos.y;

        return Mathf.Atan2(mousePos.y, mousePos.x) * Mathf.Rad2Deg;
    }

    public static Vector3 calculateDir(Transform transform)
    {
        Vector3 sp = Camera.main.WorldToScreenPoint(transform.position);
        //get position relative to camera
        return (Input.mousePosition - sp).normalized;
    }

    public static float leftJoyAngle()
    {
        float x = Input.GetAxis("LeftJoyHorizontal");
        float y = Input.GetAxis("LeftJoyVertical");
        //Debug.Log("Left: " + Mathf.Atan2(y, x) * Mathf.Rad2Deg);
        return (Mathf.Atan2(y, x) * Mathf.Rad2Deg);
    }

    public static float rightJoyAngle()
    {
        float x = Input.GetAxis("RightJoyHorizontal");
        float y = Input.GetAxis("RightJoyVertical");
        //Debug.Log("x: " + x + " y: " + y);
        return (Mathf.Atan2(y, x) * Mathf.Rad2Deg);
    }

    public static Vector3 rightDir()
    {
        float x = Input.GetAxis("RightJoyHorizontal");
        float y = Input.GetAxis("RightJoyVertical");
        return new Vector3(x, y, 1);
    }
}

