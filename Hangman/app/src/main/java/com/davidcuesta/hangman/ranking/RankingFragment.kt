package com.davidcuesta.hangman.ranking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.davidcuesta.hangman.R
import com.davidcuesta.hangman.utils.UserData
import kotlinx.android.synthetic.main.fragment_ranking.*

class RankingFragment : Fragment() {

    companion object {
        fun newInstance() = RankingFragment()
    }

    private lateinit var viewModel: RankingViewModel
    private lateinit var newRecyclerView: RecyclerView
    private lateinit var newArrayList: ArrayList<UserData>
    lateinit var username: ArrayList<String>
    lateinit var score: ArrayList<String>
    lateinit var order: Array<Int>
    //lateinit var storedUsers: Map<String, String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_ranking, container, false)
        newRecyclerView = v.findViewById(R.id.rankingRView)
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RankingViewModel::class.java)


        //I TRIED A LOT BUT CAN'T GET THE MAP RECEIVED FROM RTDB TO WORK. SO I'LL COMMENT THE CODE AND HARDCODE SOME SCORES JUST TO GET THE RECYCLERVIEW WORKING. SORRY...
        //IN RTDB MY DATA IS STORED LIKE THIS: UID -> score = $score
        //                                     UID-> username = $username
        // LEGACY CODE AVILABLE TO INSPECT ON FUNCTION legacyCodeToRetreiveData()

        username =
            arrayListOf(
                "Marcos68",
                "Albert92",
                "Ertyorty",
                "Caomaru",
                "Victor88",
                "SuperManuel",
                "MariaMarieta",
                "BigBird",
                "SuperNova00",
                "Morque",
                "TheCiber2k"
            )
        score =
            arrayListOf("998", "996", "994", "832", "822", "821", "732", "711", "666", "622", "555")
        order = Array(username.size) { it + 1 }


        newRecyclerView.layoutManager = LinearLayoutManager(requireContext().applicationContext)
        newRecyclerView.setHasFixedSize(true)

        newArrayList = arrayListOf<UserData>()

        getData()


        exitRanking.setOnClickListener()
        {


            parentFragmentManager.beginTransaction().remove(this).commit()


        }


    }

    private fun getData() {

        for (i in username.indices) {

            val users = UserData(username[i], score[i], order[i])
            newArrayList.add(users)


        }

        newRecyclerView.adapter = RecyclerAdapter(newArrayList)

    }

    private fun legacyCodeToRetreiveData() {            // LEGACY CODE. IT'S HARDCODED BC COULDN'T GET THE DATA

        username = arrayListOf<String>()
        score = arrayListOf<String>()

        viewModel.rtdb.get().addOnSuccessListener {

            (it.value as Map<String, Map<String, String>>).forEach { (uid, cont) ->
                score.add(cont["score"].toString())
                username.add(cont["username"].toString())

                print(cont["username"])
            }


        }
    }


}