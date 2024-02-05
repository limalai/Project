using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class LetsGo : MonoBehaviour
{

    public void HousePage()
    {
        SceneManager.LoadSceneAsync("HousePage");
    }

    public void FirstPage()
    {
        SceneManager.LoadSceneAsync("FirstPage");
    }

    //public int level;
    public void OpenScene(int level)
    {
        SceneManager.LoadScene("Level " + level.ToString());
    }


}
