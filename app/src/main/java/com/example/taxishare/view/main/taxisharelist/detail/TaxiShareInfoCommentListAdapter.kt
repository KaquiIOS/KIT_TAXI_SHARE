/*
 * Created by WonJongSeong on 2019-07-19
 */

package com.example.taxishare.view.main.taxisharelist.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.taxishare.R
import com.example.taxishare.app.Constant
import com.example.taxishare.data.model.Comment
import kotlinx.android.synthetic.main.item_comment.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class TaxiShareInfoCommentListAdapter :
    ListAdapter<Comment, TaxiShareInfoCommentListAdapter.CommentViewHolder>(Comment.DIFF_UTIL) {

    private lateinit var onBottomReachedListener: OnBottomReachedListener
    private lateinit var commentRemoveClickListener: CommentItemRemoveClickListener

    private val commentList: MutableList<Comment> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder =
        CommentViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_comment, parent, false))

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bind(commentList[position])

        with(holder.itemView) {

            if (position == commentList.size - 1 && ::onBottomReachedListener.isInitialized) {
                onBottomReachedListener.onBottomReached()
            }

            if (Constant.USER_ID == commentList[position].uid.toString()) {

                tv_comment_pop_up.visibility = View.VISIBLE

                // PopUp Menu Click Event 처리
                tv_comment_pop_up.onClick {

                    val popupMenu = PopupMenu(context, tv_comment_pop_up)

                    popupMenu.inflate(R.menu.menu_comment)

                    // MenuItemClick Event Listener
                    popupMenu.setOnMenuItemClickListener {
                        // 삭제
                        if (it.itemId == R.id.comment_remove && ::commentRemoveClickListener.isInitialized) {
                            commentRemoveClickListener.onClick(commentList[holder.adapterPosition].commentId)
                        }
                        false
                    }

                    popupMenu.show()
                }

            }
        }
    }

    fun insertComment(comment: Comment) {
        this.commentList.add(0, comment)
        submitList(ArrayList(commentList))
    }

    fun setComments(commentList: MutableList<Comment>) {
        this.commentList.addAll(commentList)
        submitList(ArrayList(this.commentList))
    }

    fun removeComment(commentId: Int) {

        val iter = commentList.iterator()

        while (iter.hasNext()) {
            if (iter.next().commentId == commentId) {
                iter.remove()
                break
            }
        }

        submitList(ArrayList(commentList))
    }

    fun setOnBottomReachedListener(onBottomReachedListener: OnBottomReachedListener) {
        this@TaxiShareInfoCommentListAdapter.onBottomReachedListener = onBottomReachedListener
    }

    fun setCommentRemoveClickListener(commentRemoveClickListener: CommentItemRemoveClickListener) {
        this@TaxiShareInfoCommentListAdapter.commentRemoveClickListener = commentRemoveClickListener
    }

    inner class CommentViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(comment: Comment) {
            with(comment) {
                view.tv_comment_name.text = nickname
                view.tv_comment_content.text = content
                view.tv_comment_time.text = commentDate
            }
        }
    }
}