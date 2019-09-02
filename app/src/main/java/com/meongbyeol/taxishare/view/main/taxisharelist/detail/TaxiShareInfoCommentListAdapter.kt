/*
 * Created by WonJongSeong on 2019-07-19
 */

package com.meongbyeol.taxishare.view.main.taxisharelist.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.meongbyeol.taxishare.R
import com.meongbyeol.taxishare.app.Constant
import com.meongbyeol.taxishare.data.model.Comment
import kotlinx.android.synthetic.main.item_comment.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class TaxiShareInfoCommentListAdapter :
    ListAdapter<Comment, RecyclerView.ViewHolder>(Comment.DIFF_UTIL) {

    private lateinit var onBottomReachedListener: OnBottomReachedListener
    private lateinit var commentRemoveClickListener: CommentItemRemoveClickListener

    private val commentList: MutableList<Comment> = mutableListOf()

    private var isEmptyList : Boolean = true

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            1 -> CommentViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_comment,
                    parent,
                    false
                )
            )
            else -> NoCommentViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_no_comment,
                    parent,
                    false
                )
            )
        }

    override fun getItemViewType(position: Int): Int = when (isEmptyList) {
        false -> 1
        else -> 2
    }

    override fun getItemCount(): Int = commentList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder.itemViewType == 1) {

            (holder as CommentViewHolder).bind(commentList[holder.adapterPosition])

            with(holder.itemView) {

                if (holder.adapterPosition == commentList.size - 1 && ::onBottomReachedListener.isInitialized) {
                    onBottomReachedListener.onBottomReached()
                }

                if (Constant.CURRENT_USER.studentId == commentList[holder.adapterPosition].uid) {

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
    }

    fun insertComment(comment: Comment) {

        if(isEmptyList) commentList.clear()

        isEmptyList = false

        commentList.add(0, comment)
        submitList(ArrayList(commentList))
    }

    fun setComments(newCommentList: MutableList<Comment>) {
        commentList.addAll(newCommentList)

        isEmptyList = false

        if(commentList.isEmpty()) {
            isEmptyList = true
            commentList.add(Comment())
        }

        submitList(ArrayList(commentList))
    }

    fun clear() {
        commentList.clear()
        commentList.add(Comment())
        isEmptyList = true

        submitList(ArrayList(commentList))
    }

    fun removeComment(commentId: Int) {

        val iter = commentList.iterator()

        while (iter.hasNext()) {
            if (iter.next().commentId == commentId) {
                iter.remove()
                break
            }
        }

        if(commentList.isEmpty()) {
            isEmptyList = true
            commentList.add(Comment())
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

    inner class NoCommentViewHolder(view: View) : RecyclerView.ViewHolder(view)
}