package android.mainactivity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;


import com.example.a15711.diarypractice.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Created by 15711 on 2018/10/16.
 */

public class diaryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Boolean isDuoXuan;//是否多选的标志
    private Context mContext;//上下文
    public static List<diary> mDiaryList=new ArrayList<>();//数据项列
    //键为RecyclerView中各子项的position，值为该位置复选框的选中状态
    public static HashMap<Integer,Boolean> isSelected=new HashMap<>();
    //这是有日记展示的Holder
    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView diaryContent;
        TextView diaryTime;
        CheckBox checkBox;
        public ViewHolder(View view){
            super(view);
            cardView=(CardView)view;
            diaryContent=(TextView)view.findViewById(R.id.diary_content);
            diaryTime=(TextView)view.findViewById(R.id.diary_time);
            checkBox=(CheckBox)view.findViewById(R.id.check_box);
        }
    }

    //这是无日记展示的Holder
    static class EmptyViewHolder extends RecyclerView.ViewHolder{
        View empty_view;
        TextView textView;
        public EmptyViewHolder(View view){
            super(view);
            empty_view=view;
            textView=(TextView)view.findViewById(R.id.empty_text);
        }
    }

    //适配器的构造函数
    public diaryAdapter(List<diary> diaryList,boolean isDuoXuan){
        mDiaryList=diaryList;
        this.isDuoXuan=isDuoXuan;
    }


    //数据源是否为空，为空返回-1
    @Override
    public int getItemViewType(int position) {
        if(mDiaryList.size()<=0){
            return -1;
        }
        return super.getItemViewType(position);
    }

    //创建ViewHolder并返回
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mContext==null){
            mContext=parent.getContext();
        }
        //数据源为空时返回空的子项布局
        if(viewType==-1){
            View view=LayoutInflater.from(mContext).inflate(R.layout.diary_empty_item,parent,false);
            return new EmptyViewHolder(view);
        }
        //数据源不为空时返回卡片布局
        View view= LayoutInflater.from(mContext).inflate(R.layout.diary_item,parent,false);
        return new ViewHolder(view);
    }

    //具体的处理逻辑
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        //holder为卡片布局的holder
        if (holder instanceof ViewHolder) {
            if(isDuoXuan){
                ((ViewHolder) holder).checkBox.setVisibility(View.VISIBLE);
                //当isSelected中有该位置CheckBox的显示状态时就加载，没有就设为false
                if(isSelected.containsKey(position)){
                    ((ViewHolder) holder).checkBox.setChecked(isSelected.get(position));
                }else{
                    isSelected.put(position,false);
                }
            }else{
                //单选状态时清空isSelected，并设置所有复选框不可见
                isSelected.clear();
                ((ViewHolder) holder).checkBox.setVisibility(View.GONE);
            }
            diary mDiary=mDiaryList.get(position);
            ((ViewHolder) holder).diaryContent.setText(mDiary.getContent());
            ((ViewHolder) holder).diaryTime.setText(mDiary.getTime());
        }
    }

    //告诉适配器有多少个项，以便留出足够的空间
    @Override
    public int getItemCount() {
        return mDiaryList.size()>0?mDiaryList.size():1;
    }
}
