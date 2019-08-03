/*
 * Created by WonJongSeong on 2019-07-18
 */

package com.example.taxishare.view.main.taxisharelist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.taxishare.R
import com.example.taxishare.app.Constant
import com.example.taxishare.data.mapper.TypeMapper
import com.example.taxishare.data.model.TaxiShareInfo
import kotlinx.android.synthetic.main.item_taxi_share_post.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.textColor

// ListAdapter 사용
class TaxiShareListAdapter :
    ListAdapter<TaxiShareInfo, TaxiShareListAdapter.TaxiShareInfoViewHolder>(TaxiShareInfo.DIFF_UTIL) {

    private lateinit var taxiShareInfoItemClickListener: TaxiShareInfoItemClickListener
    private lateinit var taxiShareInfoModifyClickListener: TaxiShareInfoModifyClickListener
    private lateinit var taxiShareInfoRemoveClickListener: TaxiShareInfoRemoveClickListener
    private lateinit var taxiShareParticipantBtnClickListener: TaxiShareParticipantBtnClickListener

    private val taxiShareInfoList: MutableList<TaxiShareInfo> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaxiShareInfoViewHolder =
        TaxiShareInfoViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_taxi_share_post, parent, false)
        )

    override fun onBindViewHolder(holder: TaxiShareInfoViewHolder, position: Int) {
        holder.bind(taxiShareInfoList[position])

        with(holder.itemView) {

            // btn onClick Listener 작성
            btn_taxi_share_post_participate.onClick {
                if (::taxiShareParticipantBtnClickListener.isInitialized) {
                    taxiShareParticipantBtnClickListener.onParticipantsButtonClicked(
                        taxiShareInfoList[holder.adapterPosition].id,
                        taxiShareInfoList[holder.adapterPosition].isParticipated
                    )
                }
            }

            if (Constant.USER_ID == taxiShareInfoList[position].uid) {

                tv_taxi_share_post_pop_up.visibility = View.VISIBLE

                // PopUp Menu Click Event 처리
                tv_taxi_share_post_pop_up.onClick {

                    val popupMenu = PopupMenu(context, tv_taxi_share_post_pop_up)

                    popupMenu.inflate(R.menu.menu_taxi_share_info)

                    // MenuItemClick Event Listener
                    popupMenu.setOnMenuItemClickListener {
                        // 삭제
                        if (it.itemId == R.id.taxi_share_info_remove) {
                            if (::taxiShareInfoRemoveClickListener.isInitialized) {
                                taxiShareInfoRemoveClickListener.onTaxiShareInfoRemoveClicked(
                                    taxiShareInfoList[holder.adapterPosition].id
                                )
                            }
                        } else if (it.itemId == R.id.taxi_share_info_modify) {
                            if (::taxiShareInfoModifyClickListener.isInitialized) {
                                taxiShareInfoModifyClickListener.onTaxiShareInfoModifyClicked(
                                    taxiShareInfoList[holder.adapterPosition], holder.adapterPosition
                                )
                            }
                        }

                        false
                    }
                    popupMenu.show()
                }
            }

            // 상세화면으로 넘어가는 이벤트
            onClick {
                if (::taxiShareInfoItemClickListener.isInitialized) {
                    taxiShareInfoItemClickListener.onTaxiShareInfoItemClicked(taxiShareInfoList[position])
                }
            }
        }
    }

    fun setTaxiShareParticipantsClickListener(taxiShareParticipantBtnClickListener: TaxiShareParticipantBtnClickListener) {
        this@TaxiShareListAdapter.taxiShareParticipantBtnClickListener = taxiShareParticipantBtnClickListener
    }

    fun setTaxiShareInfoItemClickListener(taxiShareInfoItemClickListener: TaxiShareInfoItemClickListener) {
        this@TaxiShareListAdapter.taxiShareInfoItemClickListener = taxiShareInfoItemClickListener
    }

    fun setTaxiShareInfoModifyClickListener(taxiShareInfoModifyClickListener: TaxiShareInfoModifyClickListener) {
        this@TaxiShareListAdapter.taxiShareInfoModifyClickListener = taxiShareInfoModifyClickListener
    }

    fun setTaxiShareInfoRemoveClickListener(taxiShareInfoRemoveClickListener: TaxiShareInfoRemoveClickListener) {
        this@TaxiShareListAdapter.taxiShareInfoRemoveClickListener = taxiShareInfoRemoveClickListener
    }

    fun setTaxiShareInfoList(taxiShareInfoList: MutableList<TaxiShareInfo>, isRefresh: Boolean) {
        if(isRefresh) {
            this.taxiShareInfoList.clear()
        }
        this.taxiShareInfoList.addAll(taxiShareInfoList)
        submitList(ArrayList(this.taxiShareInfoList))
    }

    private fun findTaxiShareInfoWithPostId(postId: String): Int {
        var idx: Int = -1

        for (i in taxiShareInfoList.indices) {
            if (postId == taxiShareInfoList[i].id) {
                idx = i
                break
            }
        }

        return idx
    }

    fun changeTaxiShareParticipateInfo(postId: String, isParticipate: Boolean) {

        val idx: Int = findTaxiShareInfoWithPostId(postId)

        if (idx != -1) {
            this.taxiShareInfoList[idx].isParticipated = isParticipate
            this.taxiShareInfoList[idx].participantsNum = when (isParticipate) {
                true -> taxiShareInfoList[idx].participantsNum + 1
                false -> taxiShareInfoList[idx].participantsNum - 1
            }
            notifyItemChanged(idx)
        }
    }

    fun removeTaxiShare(postId: String) {

        val idx: Int = findTaxiShareInfoWithPostId(postId)

        if (idx != -1) {
            taxiShareInfoList.removeAt(idx)
            submitList(ArrayList(taxiShareInfoList))
        }
    }

    fun addTaxiShareInfo(taxiShareInfo: TaxiShareInfo, isRefresh: Boolean) {
        taxiShareInfoList.add(0, taxiShareInfo)
        if (isRefresh) {
            submitList(ArrayList(taxiShareInfoList))
        }
    }

    fun updateTaxiShareInfo(taxiShareInfo: TaxiShareInfo) {
        val idx = findTaxiShareInfoWithPostId(taxiShareInfo.id)
        if (idx != -1) {
            taxiShareInfoList[idx] = taxiShareInfo
            submitList(ArrayList(taxiShareInfoList))
        }
    }

    private fun changeButtonState(view: View, text: String, @DrawableRes id: Int, @ColorRes cId: Int) {
        view.btn_taxi_share_post_participate.setBackgroundResource(id)
        view.btn_taxi_share_post_participate.text = text
        view.btn_taxi_share_post_participate.textColor = cId
    }

    inner class TaxiShareInfoViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(taxiShareInfo: TaxiShareInfo) {

            with(taxiShareInfo) {
                view.tv_taxi_share_post_nickname.text = String.format("%s (%s)", nickname, major)
                view.tv_taxi_share_post_start_time.text = TypeMapper.dateToString(startDate)
                view.tv_taxi_share_post_start_location.text = startLocation.locationName
                view.tv_taxi_share_post_end_location.text = endLocation.locationName
                view.tv_taxi_share_post_title.text = title

                if (Constant.CURRENT_USER.studentId == uid.toInt()) {
                    changeButtonState(
                        view,
                        String.format("내가 작성한 글입니다 (%d)", participantsNum),
                        R.drawable.background_already_participate_color,
                        R.color.light_gray
                    )
                    view.btn_taxi_share_post_participate.isEnabled = false
                } else if (isParticipated) {
                    changeButtonState(
                        view,
                        String.format("이미 참여중인 글입니다.(%d)", participantsNum),
                        R.drawable.background_already_participate_color,
                        R.color.light_gray
                    )
                } else {
                    changeButtonState(
                        view,
                        String.format("현재 참여 %d 명 (%d)", participantsNum, limit),
                        R.drawable.background_not_participate_color,
                        R.color.common_black
                    )
                }
            }
        }
    }
}