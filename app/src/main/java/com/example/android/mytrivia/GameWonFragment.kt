
package com.example.android.navigation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.android.mytrivia.R
import com.example.android.mytrivia.databinding.FragmentGameWonBinding


class GameWonFragment : Fragment() {
    private lateinit var binding : FragmentGameWonBinding

    private lateinit var args: GameWonFragmentArgs

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_game_won, container, false)

        // Getting the Args
        args = GameWonFragmentArgs.fromBundle(requireArguments())

        // Setting onClickListener for NextMatch Button
        binding.nextMatchButton.setOnClickListener {
            it.findNavController().navigate(GameWonFragmentDirections.actionGameWonFragmentToGameFragment())
        }

        // GameWonFragment Has option menu
        setHasOptionsMenu(true)


        return binding.root
    }

    // Methods Related to winner menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.winner_menu, menu)

        // If no app can handle sharing success then make the menu disappear
        if(null == getShareIntent().resolveActivity(requireActivity().packageManager)){
            menu.findItem(R.id.share)?.isVisible = false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        shareSuccess()
        return super.onOptionsItemSelected(item)
    }

    // We need to get intent to share the success
    private fun getShareIntent() : Intent{
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("plain/text")
            .putExtra(Intent.EXTRA_TEXT, getString(R.string.share_success_text, args.numCorrect, args.numQuestions))

        return shareIntent
    }

    // Share Success <Start Activity For Intent>
    private fun shareSuccess(){
        startActivity(getShareIntent())
    }

}
