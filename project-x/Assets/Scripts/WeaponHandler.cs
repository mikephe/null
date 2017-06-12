using UnityEngine;
using System.Collections;
using System.Collections.Generic;

public enum WeaponState
{
    Standard, Shotgun, Railgun, Launcher
}

public class WeaponHandler : MonoBehaviour {
	public List<ProjectileMovement> pm;
	public void createProjectile(int currentIndex, float angle, Vector3 dir) {
        pm[currentIndex].movement(angle, dir);
        pm[currentIndex].hitTime = Time.time;
	}
}
