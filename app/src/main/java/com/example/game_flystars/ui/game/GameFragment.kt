package com.example.game_flystars.ui.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.game_flystars.R
import com.example.game_flystars.databinding.FragmentGameBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.round
import kotlin.random.Random

class GameFragment : Fragment() {

    private lateinit var binding: FragmentGameBinding
    private var totalRace: Double = 1.00
    private var play = false
    private var elapsedTime = 0L
    private var random: Long = 0
    private lateinit var adapter: GameAdapter
    private val listInt = mutableListOf(1.78, 1.78, 1.78, 1.78, 1.78, 1.54)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGameBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        binding.apply {
            tvTotalRace.text = totalRace.toString()
            adapter.list.submitList(listInt)
        }

        binding.apply {
            imgGameHeader.setOnClickListener { findNavController().popBackStack() }
            tvHeaderRules.setOnClickListener { findNavController().navigate(R.id.action_gameFragment_to_rulesFragment) }
            btnRaceMinus.setOnClickListener { minusRace() }
            btnRacePlus.setOnClickListener { plusRace() }
            tvRaceInstance1.setOnClickListener { setRace(1.0) }
            tvRaceInstance2.setOnClickListener { setRace(2.0) }
            tvRaceInstance4.setOnClickListener { setRace(4.0) }
            tvRaceInstance5.setOnClickListener { setRace(5.0) }
            tvGameStart.setOnClickListener { game() }
        }
    }

    private fun initAdapter() {
        adapter = GameAdapter()
        binding.rvHeaderGame.adapter = adapter
    }

    private fun game() {
        when (play) {
            true -> {
                stopGame()
            }
            false -> {
                startGame()
            }
        }
        startStopwatch()
    }

    private fun plusRace() {
        if (!play) {
            totalRace += 0.01
            totalRace = round(totalRace * 100) / 100
            updateInfo()
        }
    }

    private fun minusRace() {
        if (!play) {
            totalRace -= 0.01
            totalRace = round(totalRace * 100) / 100
            updateInfo()
        }
    }

    private fun setRace(set: Double) {
        if (!play) {
            totalRace = set
            updateInfo()
        }
    }

    private fun updateInfo() {
        binding.apply {
            tvTotalRace.text = totalRace.toString()
        }
    }

    private fun startGame() {
        play = true
        elapsedTime = 0
        random = Random.nextInt(300, 5990).toLong()
        random = (random / 10) * 10
        binding.apply {
            tvRacePoint.text = String.format("%d.%02d", 0, 0)
            tvGameStart.text = "СТОП!"
            animation.progress = 0f
            tvGameStart.setBackgroundResource(R.drawable.bg_btn_stop)
            tvGameLost.visibility = View.GONE
        }
    }

    private fun stopGame() {
        play = false
        binding.apply {
            tvGameStart.text = "ИГРАТЬ!"
            tvGameStart.setBackgroundResource(R.drawable.bg_btn_start)
        }
        dialogVisible()
    }

    private fun startStopwatch() {
        CoroutineScope(Dispatchers.Main).launch {
            while (play) {
                delay(50)
                elapsedTime += 10
                updateTimerText()
            }
        }
    }

    private fun updateTimerText() {
        if (random == elapsedTime) lostGame()
        val seconds = (elapsedTime / 1000)
        val centiseconds = (elapsedTime % 1000) / 10
        binding.tvRacePoint.text = String.format("%d.%02dX", seconds, centiseconds)
    }

    private fun lostGame() {
        play = false
        val seconds = (elapsedTime / 1000)
        val centiseconds = (elapsedTime % 1000) / 10
        binding.apply {
            imgPlan.visibility = View.INVISIBLE
            animation.playAnimation()
            tvRacePoint.text = String.format("%d.%02dX", seconds, centiseconds)
            tvGameLost.visibility = View.VISIBLE
            tvGameStart.text = "ИГРАТЬ!"
            tvGameStart.setBackgroundResource(R.drawable.bg_btn_start)
        }
    }

    private fun dialogVisible() {
        CoroutineScope(Dispatchers.Main).launch {
            binding.apply {
                dialogWin.visibility = View.VISIBLE
                delay(2000)
                dialogWin.visibility = View.INVISIBLE
            }
        }
    }
}