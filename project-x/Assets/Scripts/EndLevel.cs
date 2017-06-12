using UnityEngine;
using System.Collections;

public class EndLevel : MonoBehaviour {
    public bool nextLevel = false;
    public bool endLevel = false;
    void OnCollisionEnter2D(Collision2D collInfo)
    {
        if (collInfo.gameObject.tag.Equals("Player"))
        {
            if (nextLevel)
                Application.LoadLevel(Application.loadedLevel + 1);
            if (endLevel)
                Utilities.loadEnd();
        }
    }
}
