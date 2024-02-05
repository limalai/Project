using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.SceneManagement;

public class ASynceLoader : MonoBehaviour
{
    [Header("Menu Screen")]
    [SerializeField] private GameObject loadingScreen;

    [Header("Slider")]
    [SerializeField] private Slider loadingSlider;

    public void LoadLevelBtn(string levelToLoad)
    {
        

        StartCoroutine(LoadLevelASync(levelToLoad));
    }

    IEnumerator LoadLevelASync(string levelToLoad)
    {
        loadingSlider.value = 0;
        //mainMenu.SetActive(false);
        loadingScreen.SetActive(true);
        AsyncOperation loadOperation = SceneManager.LoadSceneAsync(levelToLoad);
        loadOperation.allowSceneActivation = false;
        float progressValue = 0;


        while (!loadOperation.isDone)
        {
            //float progressValue = Mathf.Clamp01(loadOperation.progress / 0.9f);
            progressValue = Mathf.MoveTowards(progressValue, loadOperation.progress, Time.deltaTime);
            loadingSlider.value = progressValue;
            if (progressValue >= 0.9f)
            {
                loadingSlider.value = 1;
                loadOperation.allowSceneActivation = true;
            }
            yield return null;
        }
    }

}
