package com.example.newsapp.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.newsapp.R;
import com.example.newsapp.db.SearchHistory;

import org.litepal.crud.DataSupport;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> implements View.OnClickListener {
    private List<SearchHistory> searchHistoryList;
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView searchContent;
        ImageView deleteSearch;
        public ViewHolder(View view){
            super(view);
            searchContent=view.findViewById(R.id.searchRecord);
            deleteSearch=view.findViewById(R.id.deleteRecord);
            deleteSearch.setOnClickListener(SearchAdapter.this);
            view.setOnClickListener(SearchAdapter.this);
        }
    }
    public SearchAdapter(List<SearchHistory> searchHistoryList){
        this.searchHistoryList=searchHistoryList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_history,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final SearchHistory searchHistory=searchHistoryList.get(position);
        holder.searchContent.setText(searchHistory.getSearch());
        holder.deleteSearch.setTag(position);
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return searchHistoryList.size();
    }
    public enum ViewName {
        ITEM,
        PRACTISE
    }
    //自定义一个回调接口来实现Click和LongClick事件
    public interface OnItemClickListener  {
        void onItemClick(View v, ViewName viewName, int position);
        void onItemLongClick(View v);
    }

    private OnItemClickListener mOnItemClickListener;//声明自定义的接口
    public void setOnItemClickListener(OnItemClickListener  listener) {
        this.mOnItemClickListener  = listener;
    }

    public void onClick(View v) {
        int position = (int) v.getTag();      //getTag()获取数据
        if (mOnItemClickListener != null) {
            switch (v.getId()){
                case R.id.deleteRecord:
                    mOnItemClickListener.onItemClick(v, ViewName.PRACTISE, position);
                    break;
                default:
                    mOnItemClickListener.onItemClick(v, ViewName.ITEM, position);
                    break;
            }
        }
    }
}
