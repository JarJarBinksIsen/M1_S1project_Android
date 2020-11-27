package fr.yncrea.m1_s1project_android.RecyclerView;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonObject;
import java.util.Objects;

import fr.yncrea.m1_s1project_android.R;
import fr.yncrea.m1_s1project_android.interfaces.BluetoothParent;
import fr.yncrea.m1_s1project_android.models.Channel;
import fr.yncrea.m1_s1project_android.models.PowerSupply;

import static fr.yncrea.m1_s1project_android.models.PowerSupply.I;
import static fr.yncrea.m1_s1project_android.models.PowerSupply.V;

public class MainBoardViewHolder extends RecyclerView.ViewHolder {

    public Button mChannelActivation;
    public EditText mChannelValue;
    public ToggleButton mChannelType;

    public MainBoardViewHolder(@NonNull View itemView) {
        super(itemView);
        mChannelActivation = itemView.findViewById(R.id.activation);
        mChannelValue = itemView.findViewById(R.id.value);
        mChannelType = itemView.findViewById(R.id.mode);

        itemView.findViewById(R.id.item_channel_container).setOnClickListener(v -> {
            Log.d("itemViewClick", "onClick");
        });
    }

    public void setInitialDisplay(Context context, Channel channel){
        mChannelActivation.setBackgroundColor(context.getResources().getColor(channel.isActive() ? R.color.green : R.color.red));
        mChannelActivation.setText(context.getResources().getString(R.string.input, String.valueOf(channel.getId())));
        mChannelValue.setHint(context.getResources().getString(R.string.input, String.valueOf(channel.getId())));
        mChannelValue.setText(String.valueOf(channel.getCurrentValue()));
        mChannelType.setChecked(channel.getType() != (PowerSupply) V);
        mChannelType.setChecked(mChannelType.isChecked());
    }

    public void setInteractions(Context context, Channel channel){

        mChannelActivation.setOnClickListener(v -> {
            channel.setActive(!channel.isActive());
            mChannelActivation.setBackgroundColor(context.getResources().getColor(channel.isActive() ? R.color.green : R.color.red));

            ((BluetoothParent) Objects.requireNonNull(context)).getGenerator().getChannelList().get(channel.getId()).setActive(channel.isActive());
            Channel tmp = new Channel();
            tmp.setId(channel.getId());
            tmp.setActive(channel.isActive());
            Log.d("testy 2", "activation");
            ((BluetoothParent) Objects.requireNonNull(context)).sendData(tmp);

        });

        /*mChannelType.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Log.d("testy 3", ""+channel.getId());
            channel.setType(isChecked ? I : V);

            ((BluetoothParent) Objects.requireNonNull(context)).getGenerator().getChannelList().get(channel.getId()).setType(channel.getType());
            Channel tmp = new Channel();
            tmp.setId(channel.getId());
            tmp.setType(channel.getType());
            ((BluetoothParent) Objects.requireNonNull(context)).sendData(tmp);

        });*/
        mChannelType.setOnClickListener(v -> {
            Log.d("testy click", "mChannelType");
            channel.setType(mChannelType.isChecked() ? I : V);

            ((BluetoothParent) Objects.requireNonNull(context)).getGenerator().getChannelList().get(channel.getId()).setType(channel.getType());
            Channel tmp = new Channel();
            tmp.setId(channel.getId());
            tmp.setType(channel.getType());
            ((BluetoothParent) Objects.requireNonNull(context)).sendData(tmp);

        });

    }
}
