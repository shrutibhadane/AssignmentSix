package com.habit_builder.assignment_four

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.habit_builder.assignment_three.Habit
import com.habit_builder.assignmentsix.R
import java.text.SimpleDateFormat
import java.util.*


class HabitAdapter(
    private val habitList: List<Habit>,
    private val onCheckIn: (Habit, Int) -> Unit
) : RecyclerView.Adapter<HabitAdapter.HabitViewHolder>() {

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    class HabitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv_habit_name: TextView = itemView.findViewById(R.id.tv_habit_name)
        val tv_streak_count: TextView = itemView.findViewById(R.id.tv_streak_count)
        val cb_habit_done: CheckBox = itemView.findViewById(R.id.cb_habit_done)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_habit, parent, false)
        return HabitViewHolder(view)
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        val habit = habitList[position]
        holder.tv_habit_name.text = habit.name
        
        val today = dateFormat.format(Date())
        val isCompletedToday = habit.lastCompletedDate == today
        
        holder.cb_habit_done.setOnCheckedChangeListener(null) // Prevent recursive calls
        holder.cb_habit_done.isChecked = isCompletedToday
        holder.cb_habit_done.isEnabled = !isCompletedToday // Once checked, it's done for today

        updateStreakUI(holder, habit.streak)

        holder.cb_habit_done.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                onCheckIn(habit, position)
            }
        }
    }

    private fun updateStreakUI(holder: HabitViewHolder, streak: Int) {
        val streakText = if (streak > 0) "🔥 $streak Day Streak" else "$streak Day Streak"
        holder.tv_streak_count.text = streakText
        val context = holder.itemView.context
        
        // Dynamic Coloring
        val streakColor = when {
            streak >= 10 -> ContextCompat.getColor(context, R.color.primary_dark) // High Streak
            streak >= 5 -> ContextCompat.getColor(context, R.color.accent)       // Medium Streak
            streak >= 1 -> ContextCompat.getColor(context, R.color.primary)      // Active Streak
            else -> Color.GRAY                                                  // No Streak
        }
        
        holder.tv_streak_count.setTextColor(streakColor)

        // Animation for increase (only if it's a visible update)
        if (streak > 0) {
            holder.tv_streak_count.animate()
                .scaleX(1.1f)
                .scaleY(1.1f)
                .setDuration(150)
                .withEndAction {
                    holder.tv_streak_count.animate()
                        .scaleX(1.0f)
                        .scaleY(1.0f)
                        .setDuration(150)
                        .start()
                }.start()
        }
    }

    override fun getItemCount(): Int = habitList.size
}
