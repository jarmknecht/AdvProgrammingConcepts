package recyclerview;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.jonathan.familymapserver.R;

public class ParentViewHolder extends com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder {
    public TextView title;
    public ImageButton icon;

    public ParentViewHolder(View itemView) {
        super(itemView);

        title = (TextView) itemView.findViewById(R.id.listParentTitle);
        icon = (ImageButton) itemView.findViewById(R.id.expandArrow);
    }

}
