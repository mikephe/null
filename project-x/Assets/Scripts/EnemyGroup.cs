using UnityEngine;
using System.Collections;

public class EnemyGroup : MonoBehaviour {

	
	// Update is called once per frame
	void Update () {
        int count = 0;
	    foreach (Transform t in GetComponentsInChildren<Transform>())
        {
            count++;
        }
        if (count <= 1)
        {
            Destroy(gameObject);
        }
	}
}
