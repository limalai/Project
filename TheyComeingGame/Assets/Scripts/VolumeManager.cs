using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.Audio;
using UnityEngine.UI;

public class VolumeManager : MonoBehaviour
{
    [SerializeField] Slider volumesSlider;
    public AudioMixer _audio;
    public GameObject ON;
    public GameObject OFF;

    private void Start()
    {
        if (!PlayerPrefs.HasKey("MusicVolume"))
        {
            PlayerPrefs.SetFloat("MusicVolume", 1);
            load();
        }
        else
        {
            load();
        }
    }
    public void On()
    {
        AudioListener.volume = 0;
        ON.SetActive(false);
        OFF.SetActive(true);
    }

    public void Off()
    {
        AudioListener.volume = 1;
        OFF.SetActive(false);
        ON.SetActive(true);
    }

    public void ChangeVolume()
    {
        AudioListener.volume = volumesSlider.value;
        Save();
    }

    public void load()
    {
        volumesSlider.value = PlayerPrefs.GetFloat("MusicVolume");
    }

    public void Save()
    {
        PlayerPrefs.SetFloat("MusicVolume", volumesSlider.value);
    }
}
