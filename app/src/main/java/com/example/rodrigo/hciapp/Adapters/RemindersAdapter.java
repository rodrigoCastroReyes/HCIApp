package com.example.rodrigo.hciapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.rodrigo.hciapp.Model.Reminder;
import com.example.rodrigo.hciapp.R;
import com.example.rodrigo.hciapp.Utils.DateUtils;
import com.example.rodrigo.hciapp.Utils.ReminderUtils;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by rodrigo on 23/01/16.
 */
public class RemindersAdapter extends ArrayAdapter<Reminder> {
    private ArrayList<Reminder>reminders;
    private Context context;

    public RemindersAdapter(Context context, int resource, ArrayList<Reminder> reminders) {
        super(context, resource,reminders);
        this.reminders = reminders;
        this.context = context;
    }

    static class ViewHolder{
        public TextView title;
        public TextView fecha;
        public TextView hora;
        public View estado;
    }

    public ArrayList<Reminder> getReminders() {
        return reminders;
    }

    public RemindersAdapter setReminders(ArrayList<Reminder> reminders) {
        this.reminders = reminders;
        return this;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.reminder_element, parent, false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.title = (TextView)convertView.findViewById(R.id.titleReminder);
            viewHolder.fecha = (TextView)convertView.findViewById(R.id.dateReminder);
            viewHolder.hora = (TextView)convertView.findViewById(R.id.timeReminder);
            viewHolder.estado = convertView.findViewById(R.id.stateReminder);
            convertView.setTag(viewHolder);
        }
        ViewHolder holder = (ViewHolder)convertView.getTag();
        Reminder currentReminder = this.reminders.get(position);
        holder.title.setText(currentReminder.getTitle());

        String fecha = DateUtils.showDate(currentReminder.getDate());
        holder.fecha.setText(fecha);

        String hora = DateUtils.convertTimeToString(currentReminder.getDate());
        holder.hora.setText(hora);
        holder.estado.setBackgroundColor( ReminderUtils.getColorByDate(currentReminder.getDate()));
        return convertView;
    }
}
