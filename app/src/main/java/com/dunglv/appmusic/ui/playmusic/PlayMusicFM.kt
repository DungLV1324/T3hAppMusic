package com.dunglv.appmusic.ui.playmusic

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.SeekBar
import com.bumptech.glide.Glide
import com.dunglv.appmusic.App
import com.dunglv.appmusic.common.Constant
import com.dunglv.appmusic.common.Constant.EVENT_PLAY_MUSIC
import com.dunglv.appmusic.common.Constant.EVENT_START_MUSIC
import com.dunglv.appmusic.common.Constant.EVENT_UPDATE_TIME
import com.dunglv.appmusic.common.Constant.KEY
import com.dunglv.appmusic.common.Constant.KEY_COMEBACK_MUSIC
import com.dunglv.appmusic.common.Constant.KEY_NEXT_MUSIC
import com.dunglv.appmusic.common.Constant.KEY_PLAY_MUSIC
import com.dunglv.appmusic.common.Constant.KEY_SEEK_TO_MUSIC
import com.dunglv.appmusic.common.Constant.KEY_START_MUSIC
import com.dunglv.appmusic.common.Constant.KEY_URI_MUSIC
import com.dunglv.appmusic.common.Constant.MUSIC
import com.dunglv.appmusic.common.Constant.MUSIC_STRING
import com.dunglv.appmusic.common.Constant.SEEK_TO_MUSIC
import com.dunglv.appmusic.common.Constant.TYPE
import com.dunglv.appmusic.common.MessageEventMusic
import com.dunglv.appmusic.service.MusicService
import com.dunglv.appmusic.ui.MainActivity
import com.dunglv.appmusic.ui.MainVM
import com.dunglv.appmusic.ui.base.BaseBindingFragment
import com.dunglv.appmusic.ui.base.observeWithCatch
import com.dunglv.appmusic.ui.bottonshettfragment.BottomSheetMenuPlayMusic
import com.dunglv.appmusic.utils.extention.setOnSafeClick
import com.dunglv.appmusic.utils.extention.shareMusic
import com.dunglv.appmusic.utils.extention.startService
import com.dunglv.appmusic.utils.getTimeFromDuration
import com.dunglv.appmusickl.R
import com.dunglv.appmusickl.databinding.FragmentPlayMusicBinding
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class PlayMusicFM : BaseBindingFragment<FragmentPlayMusicBinding, MainVM>() {
    private var progressPos: Int = 0
    private var duration: Int = 0
    private var uri: String = ""
    private lateinit var sharedPreferences: SharedPreferences
    private var cout = 0
    private val handler = Handler()
    private var isCheck = false

    override fun getViewModel(): Class<MainVM> = MainVM::class.java

    override val layoutId: Int get() = R.layout.fragment_play_music

    override fun onCreatedView(view: View?, savedInstanceState: Bundle?) {
        initData()
        onClick()
        setSeekBar()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        EventBus.getDefault().register(this)
    }

    private fun setSeekBar() {
        binding.sbMusic.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    tasselSeekBar(progress)
                }
                progressPos = progress
                handler.removeCallbacksAndMessages(null)
                handler.postDelayed({
                    binding.sbMusic.progress = progress + 1000
                }, 1000)

            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

    }

    private val bottomSheetMenuPlayMusic: BottomSheetMenuPlayMusic by lazy {
        BottomSheetMenuPlayMusic().apply { iClick = {} }
    }

    private fun onClick() {
        binding.imHead.setOnSafeClick {
            checkHeart()
            if (isCheck) {
                toast(getString(R.string.song_has_been_added_to_favorites))
            } else {
                toast(getString(R.string.song_has_been_removed_from_favorites))
            }
        }

        binding.imBack.setOnSafeClick { exit() }

        binding.imMenu.setOnSafeClick { showMenu() }

        binding.imPause.setOnSafeClick { navigateAction(uri, KEY_PLAY_MUSIC) }

        binding.imNext.setOnSafeClick {
            navigateAction(uri, KEY_NEXT_MUSIC)
            previousNextMusic()
        }

        binding.imComeBack.setOnSafeClick {
            navigateAction(uri, KEY_COMEBACK_MUSIC)
            previousMusic()
        }

        binding.imSearch.setOnSafeClick {
            context?.shareMusic(uri)
        }

        binding.imRepeat.setOnSafeClick {
            cout++
            when (cout) {
                0 -> {
                    iClickRepeat(R.drawable.ic_repeatn, getString(R.string.playlist_is_repeated))
                    saveToken(cout)
                }

                1 -> {
                    iClickRepeat(R.drawable.ic_repeat, getString(R.string.the_song_is_repeated))
                    saveToken(cout)
                }

                2 -> {
                    saveToken(cout)
                    iClickRepeat(R.drawable.ic_sapxep1, getString(R.string.play_songs_randomly))
                    cout = -1
                }
            }
        }
    }

    private fun initData() {
        arguments?.getString(Constant.KEY_URI_MUSIC_CURRENT)?.let { uri ->
            this.uri = uri
            mainVM.getMusicWithUri(uri)
            context?.let {
                Intent(context, MusicService::class.java).apply {
                    action = KEY_START_MUSIC
                    putExtra(KEY_URI_MUSIC, uri)
                    startService(it)
                }
                saveTokenUri(uri)
            }
        }

        mainVM.musicPlayLiveData.observeWithCatch(viewLifecycleOwner) {
            it.let { itMusic ->
                context?.let { it1 ->
                    Glide.with(it1)
                        .load(itMusic.imageBitmap)
                        .placeholder(R.drawable.ic_music1)
                        .error(R.drawable.ic_music1)
                        .into(binding.imAvatar)

                    Glide.with(it1)
                        .load(itMusic.imageBitmap)
                        .placeholder(R.drawable.ic_music1)
                        .error(R.drawable.ic_music1)
                        .into(binding.imBackground)
                }
                binding.tvNameSong.text = itMusic.nameSong
                binding.tvNameSing.text = itMusic.nameSing
                if (!itMusic.isHeart) {
                    binding.imHead.setImageResource(R.drawable.ic_heart)
                } else {
                    binding.imHead.setImageResource(R.drawable.ic_heart_read)
                }
                isCheck = !itMusic.isHeart
            }
        }
    }

    private fun exit() = (activity as MainActivity).navController.popBackStack()

    private fun showMenu() =
        bottomSheetMenuPlayMusic.show(childFragmentManager, bottomSheetMenuPlayMusic.tag)

    private fun checkHeart() = mainVM.checkHeartMusicPlayer()

    private fun tasselSeekBar(progress: Int) {
        context?.let {
            Intent(it, MusicService::class.java).apply {
                action = KEY_SEEK_TO_MUSIC
                putExtra(SEEK_TO_MUSIC, progress)
                startService(it)
            }
        }
    }

    private fun navigateAction(uri: String, key: String) {
        uri.let {
            context?.let {
                Intent(context, MusicService::class.java).apply {
                    runCatching {
                        action = key
                        startService(it)
                    }.onFailure { it1 -> it1.printStackTrace() }
                }
            }
        }
    }

    private fun iClickRepeat(im: Int, title: String) {
        binding.imRepeat.setImageResource(im)
        toast(title)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(messageEvent: MessageEventMusic) {
        when (messageEvent.type) {
            EVENT_PLAY_MUSIC -> {
                setPlayMusic(messageEvent)
            }

            EVENT_START_MUSIC -> {
                setStartMusic(messageEvent)
            }

            EVENT_UPDATE_TIME -> {
                updateTime(messageEvent)
            }
        }
    }

    private fun setPlayMusic(messageEvent: MessageEventMusic) {
        val isPlaying = messageEvent.booleanValue
        if (isPlaying) {
            binding.imPause.setImageResource(R.drawable.ic_play1)
            handler.removeCallbacksAndMessages(null)
            handler.postDelayed({
                binding.sbMusic.progress = progressPos + 1000
            }, 1000)
        } else {
            binding.imPause.setImageResource(R.drawable.ic_pause1)
            handler.removeCallbacksAndMessages(null)
        }
    }

    private fun updateTime(messageEvent: MessageEventMusic) {
        binding.tvTimeStart.text = messageEvent.time
    }

    private fun setStartMusic(messageEvent: MessageEventMusic) {
        binding.sbMusic.progress = 1
        duration = messageEvent.intValue
        binding.imPause.setImageResource(R.drawable.ic_play1)
        binding.sbMusic.max = duration
        binding.tvTimeEnd.text = duration.getTimeFromDuration()
        mainVM.getMusicWithUri(messageEvent.uri)
    }

    private fun setRecently(uri: String?) {
        uri?.let { mainVM.insertRecent(it) }
    }

    private fun saveToken(type: Int) {
        sharedPreferences = activity!!.getSharedPreferences(MUSIC, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor?.putInt(TYPE, type)
        editor?.apply()
    }

    private fun saveTokenUri(type: String) {
        sharedPreferences =
            activity!!.getSharedPreferences(MUSIC_STRING, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor?.putString(KEY, type)
        editor?.apply()
    }

    private fun previousNextMusic() = App.instance.listAllMusic.let { allMusic ->
        val posPrevious = mainVM.getPreviousNextMusic(uri)
        uri = allMusic[posPrevious].uri ?: ""
        setRecently(uri)
    }

    private fun previousMusic() = App.instance.listAllMusic.let { allMusic ->
        val posPrevious = mainVM.getPreviousMusic(uri)
        uri = allMusic[posPrevious].uri ?: ""
        setRecently(uri)
    }
}