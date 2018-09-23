package recyclerview;

import android.view.View;
import com.joanzapata.iconify.widget.IconTextView;
import android.widget.TextView;

import com.example.jonathan.familymapserver.R;

public class ChildViewHolder extends com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder {
    public TextView title;
    public TextView subTitle;
    public IconTextView iconTextView;
    public View itemView;

    public ChildViewHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
        title = (TextView) itemView.findViewById(R.id.listChildItemTitle);
        subTitle = (TextView) itemView.findViewById(R.id.listChildItemSubTitle);
        iconTextView = (IconTextView) itemView.findViewById(R.id.listChildIcon);
    }

    public void setOnClickListener(final AbstractChildListItem object, final OnChildListItemClickListener listener) {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onChildListItemClick(object);
            }
        });
    }
}
