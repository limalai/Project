using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class LextLevel : MonoBehaviour
{
    public int loadNextLevel;
    // Start is called before the first frame update
    void Start()
    {
        loadNextLevel = SceneManager.GetActiveScene().buildIndex + 1;
    }

    // Update is called once per frame
    public void OnTriggerEnter(Collider other)
    {
        SceneManager.LoadScene(loadNextLevel);
        if (loadNextLevel > PlayerPrefs.GetInt("CurrentLevel"))
        {
            PlayerPrefs.SetInt("CurrentLevel", loadNextLevel);
        }
    }
}
