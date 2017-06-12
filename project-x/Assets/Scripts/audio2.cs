using UnityEngine;
using System.Collections;

public class audio2 : MonoBehaviour {
    void Awake()
    {
        // see if we've got game music still playing
        
        GameObject gameMusic = GameObject.Find("GameMusic");
        if (gameMusic) {
            // kill game music
            Destroy(gameMusic);
        }

    }
}
