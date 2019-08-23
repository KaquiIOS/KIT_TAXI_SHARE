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
    ListAdapter<TaxiShareInfo, RecyclerView.ViewHolder>(TaxiShareInfo.DIFF_UTIL) {

    private lateinit var taxiShareInfoItemClickListener: TaxiShareInfoItemClickListener
    private lateinit var taxiShareInfoModifyClickListener: TaxiShareInfoModifyClickListener
    private lateinit var taxiShareInfoRemoveClickListener: TaxiShareInfoRemoveClickListener
    private lateinit var taxiShareParticipantBtnClickListener: TaxiShareParticipantBtnClickListener

    private val taxiShareInfoList: MutableList<TaxiShareInfo> = mutableListOf()

    private var lastPosition = -1


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (taxiShareInfoList.isEmpty()) {
            false -> TaxiShareInfoViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_taxi_share_post, parent, false)
            )
            else -> NoTaxiShareInfoViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_no_taxi_share, parent, false)
            )
        }

    override fun getItemViewType(position: Int): Int = when (taxiShareInfoList.isEmpty()) {
        false -> 1
        else -> 2
    }

    override fun getItemCount(): Int = when (taxiShareInfoList.isEmpty()) {
        true -> 1
        else -> taxiShareInfoList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder.itemViewType == 1) {
            (holder as TaxiShareInfoViewHolder).bind(taxiShareInfoList[position])


            with(holder.itemView) {

                // btn onClick Listener 작성
                btn_taxi_share_post_participate.onClick {
                    if (::taxiShareParticipantBtnClickListener.isInitialized) {
                        taxiShareParticipantBtnClickListener.onParticipantsButtonClicked(
                            taxiShareInfoList[holder.adapterPosition].id,
                            taxiShareInfoList[holder.adapterPosition].isParticipated,
                            taxiShareInfoList[holder.adapterPosition].startLocation.locationName,
                            taxiShareInfoList[holder.adapterPosition].endLocation.locationName
                        )
                    }
                }

                if (Constant.CURRENT_USER.studentId.toString() == taxiShareInfoList[position].uid) {

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
                                        taxiShareInfoList[holder.adapterPosition],
                                        holder.adapterPosition
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
    }

    fun setTaxiShareParticipantsClickListener(taxiShareParticipantBtnClickListener: TaxiShareParticipantBtnClickListener) {
        this@TaxiShareListAdapter.taxiShareParticipantBtnClickListener =
            taxiShareParticipantBtnClickListener
    }

    fun setTaxiShareInfoItemClickListener(taxiShareInfoItemClickListener: TaxiShareInfoItemClickListener) {
        this@TaxiShareListAdapter.taxiShareInfoItemClickListener = taxiShareInfoItemClickListener
    }

    fun setTaxiShareInfoModifyClickListener(taxiShareInfoModifyClickListener: TaxiShareInfoModifyClickListener) {
        this@TaxiShareListAdapter.taxiShareInfoModifyClickListener =
            taxiShareInfoModifyClickListener
    }

    fun setTaxiShareInfoRemoveClickListener(taxiShareInfoRemoveClickListener: TaxiShareInfoRemoveClickListener) {
        this@TaxiShareListAdapter.taxiShareInfoRemoveClickListener =
            taxiShareInfoRemoveClickListener
    }

    fun setTaxiShareInfoList(taxiShareList: MutableList<TaxiShareInfo>, isRefresh: Boolean) {
        if (isRefresh) {
            taxiShareInfoList.clear()
        }

        taxiShareInfoList.addAll(taxiShareList)

        submitList(ArrayList(taxiShareInfoList))
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

    private fun changeButtonState(
        view: View,
        text: String, @DrawableRes id: Int, @ColorRes cId: Int
    ) {
        view.btn_taxi_share_post_participate.setBackgroundResource(id)
        view.btn_taxi_share_post_participate.text = text
        view.btn_taxi_share_post_participate.textColor = cId
    }

    inner class NoTaxiShareInfoViewHolder(view: View) : RecyclerView.ViewHolder(view)

    inner class TaxiShareInfoViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(taxiShareInfo: TaxiShareInfo) {


            with(taxiShareInfo) {
                view.tv_taxi_share_post_nickname.text =
                    String.format(
                        view.resources.getString(R.string.nickname_format),
                        nickname,
                        major
                    )
                view.tv_taxi_share_post_start_time.text =
                    String.format(
                        view.resources.getString(R.string.taxi_list_start_time),
                        TypeMapper.dateToString(startDate)
                    )

                view.tv_taxi_share_post_start_location.text = startLocation.locationName
                view.tv_taxi_share_post_end_location.text = endLocation.locationName
                view.tv_taxi_share_post_title.text = title

                view.tv_taxi_share_post_party_num.text =
                    String.format(
                        view.resources.getString(R.string.taxi_list_party_num),
                        participantsNum, limit
                    )

                if (System.currentTimeMillis() > startDate.time + Constant.ALARM_NOTIFY_TIME) {
                    changeButtonState(
                        view,
                        view.resources.getString(R.string.taxi_share_closed),
                        R.drawable.background_already_participate_color,
                        R.color.light_gray
                    )
                } else if (Constant.CURRENT_USER.studentId == uid.toInt()) {
                    changeButtonState(
                        view,
                        String.format(view.resources.getString(R.string.my_taxi_share_title)),
                        R.drawable.background_already_participate_color,
                        R.color.light_gray
                    )
                    view.btn_taxi_share_post_participate.isEnabled = false
                } else if (isParticipated) {
                    changeButtonState(
                        view,
                        String.format(view.resources.getString(R.string.already_participate_taxi_share_title)),
                        R.drawable.background_already_participate_color,
                        R.color.light_gray
                    )
                } else {
                    changeButtonState(
                        view,
                        String.format(view.resources.getString(R.string.taxi_share_participants_num)),
                        R.drawable.background_not_participate_color,
                        R.color.common_black
                    )
                }
            }
        }
    }
}