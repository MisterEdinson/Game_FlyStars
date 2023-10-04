package com.example.game_flystars.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.game_flystars.R
import com.example.game_flystars.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnStartGame.setOnClickListener { findNavController().navigate(R.id.action_homeFragment_to_gameFragment) }
            btnRulesGame.setOnClickListener { findNavController().navigate(R.id.action_homeFragment_to_rulesFragment) }
            btnExitGame.setOnClickListener { activity?.finish() }
        }
    }
}