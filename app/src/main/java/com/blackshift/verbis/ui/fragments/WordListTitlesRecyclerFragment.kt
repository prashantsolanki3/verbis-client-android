package com.blackshift.verbis.ui.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.blackshift.verbis.R
import com.blackshift.verbis.rest.model.wordlist.WordList
import com.blackshift.verbis.ui.activity.WordListViewPagerActivity
import com.blackshift.verbis.ui.viewholders.WordListTitleViewHolder
import com.blackshift.verbis.utils.listeners.WordListArrayListener
import com.blackshift.verbis.utils.manager.WordListManager
import com.firebase.client.FirebaseError
import io.github.prashantsolanki3.snaplibrary.snap.adapter.SnapAdapter
import io.github.prashantsolanki3.snaplibrary.snap.layout.viewholder.SnapViewHolder
import io.github.prashantsolanki3.snaplibrary.snap.layout.wrapper.SnapSelectableLayoutWrapper
import io.github.prashantsolanki3.snaplibrary.snap.listeners.touch.SnapOnItemClickListener

/**
 * A placeholder fragment containing a simple view.
 */
class WordListTitlesRecyclerFragment : Fragment() {

    lateinit internal var view: View
    lateinit internal var wordlistTitlesRecycler: RecyclerView
    lateinit  internal var wordListSnapAdapter: SnapAdapter<WordList>
    lateinit internal var context: Context

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        view = inflater!!.inflate(R.layout.fragment_word_list, container, false)

        wordlistTitlesRecycler = view.findViewById(R.id.wordlist_title_recycler) as RecyclerView

        context = getContext()
        wordListManager = WordListManager(getContext())
        populateViews()

        return view
    }


    lateinit internal var wordListManager: WordListManager

    internal fun populateViews() {
        wordlistTitlesRecycler.setHasFixedSize(true)

        val snapLayoutWrapper = SnapSelectableLayoutWrapper(WordList::class.java, WordListTitleViewHolder::class.java, R.layout.wordlist_title_item, 1, true)

        wordListSnapAdapter = SnapAdapter<WordList>(context, snapLayoutWrapper, wordlistTitlesRecycler)
        wordListSnapAdapter.setOnItemClickListener(object :SnapOnItemClickListener{
            override fun onItemLongPress(p0: SnapViewHolder<*>?, p1: View?, p2: Int) {
            }

            override fun onItemClick(p0: SnapViewHolder<*>?, p1: View?, p2: Int) {
                startActivity(WordListViewPagerActivity.createIntent(context,p2))
            }
        })

        val layoutManager = GridLayoutManager(context, 2)
        wordlistTitlesRecycler.layoutManager = layoutManager
        wordlistTitlesRecycler.adapter = wordListSnapAdapter
        wordListManager.getAllWordLists(object : WordListArrayListener() {
            override fun onSuccess(wordList: List<WordList>?) {
                wordListSnapAdapter.set(wordList)
            }

            override fun onFailure(firebaseError: FirebaseError) {

            }
        })
    }
}