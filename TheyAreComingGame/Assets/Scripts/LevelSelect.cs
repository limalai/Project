using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;
using UnityEngine.UI;

public class LevelSelect : MonoBehaviour
{
    //public int level;
    public Button[] levelButtons;

    // Start is called before the first frame update
    void Start()
    {
        int CurrentLevel = PlayerPrefs.GetInt("CurrentLevel", 2);
        for (int i = 0; i < levelButtons.Length; i++)
        {
            if (i + 2 > CurrentLevel)
            {
                levelButtons[i].interactable = false;
            }
        }
    }


    public void Reset()
    {
        PlayerPrefs.DeleteAll();
    }
}
