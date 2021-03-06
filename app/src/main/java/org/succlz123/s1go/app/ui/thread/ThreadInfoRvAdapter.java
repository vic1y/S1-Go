package org.succlz123.s1go.app.ui.thread;

import com.facebook.drawee.view.SimpleDraweeView;

import org.succlz123.s1go.app.MainApplication;
import org.succlz123.s1go.app.R;
import org.succlz123.s1go.app.api.bean.ThreadInfo;
import org.succlz123.s1go.app.ui.base.BaseRvViewHolder;
import org.succlz123.s1go.app.widget.AppSize;
import org.succlz123.s1go.app.utils.s1.S1UidToAvatarUrl;
import org.succlz123.s1go.app.utils.common.SysUtils;
import org.succlz123.s1go.app.utils.fromhtml.ImageLinkParser;
import org.succlz123.s1go.app.utils.fromhtml.SpannedImageGetter;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by succlz123 on 16/4/24.
 */
public class ThreadInfoRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ThreadInfo.VariablesEntity.PostlistEntity> mThreadInfoList = new ArrayList<>();

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ThreadInfoRvViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int position) {
        if (mThreadInfoList == null) {
            return;
        }
        if (viewHolder instanceof ThreadInfoRvViewHolder) {

            String url = S1UidToAvatarUrl.getAvatar(mThreadInfoList.get(position).authorid);
            ((ThreadInfoRvViewHolder) viewHolder).mAvatarImg.setImageURI(Uri.parse(url));

            ((ThreadInfoRvViewHolder) viewHolder).mName.setText(mThreadInfoList.get(position).author);
            ((ThreadInfoRvViewHolder) viewHolder).mTime.setText(Html.fromHtml(mThreadInfoList.get(position).dateline));

            int mCurrentpagerNum = 1;
            if (mCurrentpagerNum == 1) {
                if (position == 0) {
                    ((ThreadInfoRvViewHolder) viewHolder).mNum.setText(MainApplication.getInstance().getString(R.string.louzhu));
                } else if (position > 0) {
                    ((ThreadInfoRvViewHolder) viewHolder).mNum.setText("" + position + MainApplication.getInstance().getString(R.string.lou));
                }
            } else {
                ((ThreadInfoRvViewHolder) viewHolder).mNum.setText("" + ((30 * (mCurrentpagerNum - 1)) + position) + MainApplication.getInstance().getString(R.string.lou));
            }

            ((ThreadInfoRvViewHolder) viewHolder).mReviews.setMovementMethod(ImageLinkParser.getInstance());
            String reply = mThreadInfoList.get(position).message;
            if (TextUtils.isEmpty(reply)) {
                ((ThreadInfoRvViewHolder) viewHolder).mReviews.setText("null");
            } else {
                Spanned spanned = Html.fromHtml(reply, new SpannedImageGetter(((ThreadInfoRvViewHolder) viewHolder).mReviews, new AppSize(4, 4)), null);
                ((ThreadInfoRvViewHolder) viewHolder).mReviews.setText(spanned);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mThreadInfoList != null ? mThreadInfoList.size() : 0;
    }

    public void setData(List<ThreadInfo.VariablesEntity.PostlistEntity> threadInfoList) {
        mThreadInfoList.addAll(threadInfoList);
        notifyDataSetChanged();
    }

    private static class ThreadInfoRvViewHolder extends BaseRvViewHolder {
        private SimpleDraweeView mAvatarImg;
        private TextView mName;
        private TextView mTime;
        private TextView mNum;
        private TextView mReviews;

        public ThreadInfoRvViewHolder(View itemView) {
            super(itemView);
            mAvatarImg = f(itemView, R.id.author_img);
            mName = f(itemView, R.id.author_name);
            mTime = f(itemView, R.id.author_time);
            mNum = f(itemView, R.id.author_num);
            mReviews = f(itemView, R.id.author_content);
        }

        public static ThreadInfoRvViewHolder create(ViewGroup parent) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.recycler_view_item_thread_info, parent, false);
            return new ThreadInfoRvViewHolder(view);
        }
    }

    public static class ItemDecoration extends RecyclerView.ItemDecoration {

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDraw(c, parent, state);
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
            int margin = SysUtils.dp2px(parent.getContext(), 5);
            if (position == 0) {
                outRect.set(0, margin, 0, margin);
            } else {
                outRect.set(0, 0, 0, margin);
            }
        }
    }
}
