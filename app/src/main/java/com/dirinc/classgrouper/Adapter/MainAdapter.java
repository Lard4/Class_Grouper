package com.dirinc.classgrouper.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dirinc.classgrouper.Activity.*;
import com.dirinc.classgrouper.Info.*;
import com.dirinc.classgrouper.R;
import com.dirinc.classgrouper.Util.NavigationDrawer;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private HashMap<Integer, Student> studentData;
    private List<ClassInfo> classData;
    private HashMap<Integer, String> thisClass;
    private NavigationDrawer navigationDrawer;

    private boolean isMain;
    private View layout;
    private Context context;
    private AppCompatActivity activity;

    /** Roster Activity **/
    public MainAdapter(ArrayList<ClassInfo> classData, int nClass, Context context) {
        super();
        this.isMain = false;
        this.context = context;
        this.classData = classData;
        this.studentData = new HashMap<>();
        this.thisClass = classData.get(nClass).getMap();

        for (int i = 0; i < thisClass.size(); i++) {
            Student student = new Student();
            if (getInitials(i) != null) {
                student.setInitials(getInitials(i))
                        .setColor(generateColor())
                        .setName(getName(i));
                studentData.put(i, student);
            }
        }
    }

    /** Main Activity **/
    public MainAdapter(ArrayList<ClassInfo> classData, View layout, Context context,
                       NavigationDrawer navigationDrawer, AppCompatActivity activity) {
        super();
        this.isMain = true;
        this.layout = layout;
        this.classData = classData;
        this.context = context;
        this.navigationDrawer = navigationDrawer;
        this.activity = activity;
        addClassesToDrawer();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int dontUse) {
        View v;
        if (isMain) {
            v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.class_card, viewGroup, false);
        } else {
            v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.student_card, viewGroup, false);
        }
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        final int position = viewHolder.getAdapterPosition();

        if (isMain) {
            final ClassInfo classInfo = classData.get(position);

            viewHolder.classCardTitle.setText(classInfo.getCardName());
            if (!viewHolder.classCardTitle.getText().equals("No Classes!")) {
                viewHolder.classCardToolbar.inflateMenu(R.menu.menu_delete);
                viewHolder.classCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Intent intent = new Intent(context, ClassRoster.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("bzofghia", position);
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    }
                });
            }
            viewHolder.classCardStudentCount.setText(classInfo.getCardStudentCount());
            viewHolder.classCardColor.setBackgroundColor(classInfo.getCardColor());
            viewHolder.classCardToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(layout.getContext());
                    dialog.setTitle("Warning!")
                            .setMessage("You are about to delete a class. Are you sure you" +
                                    "want to do this? This action cannot be undone.")
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialoginterface, int i) {
                                    dialoginterface.cancel();
                                }
                            })
                            .setPositiveButton("OK, M8!", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialoginterface, int idk) {
                                    removeClass(position);
                                    Snackbar.make(layout, "Class '" + classInfo.getCardName() + "' deleted", Snackbar.LENGTH_LONG)
                                            .setAction("Dandy!", new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) { /*69*/ }
                                            })
                                            .setActionTextColor(Color.parseColor("#FFFFC107"))
                                            .show();
                                }
                            }).show();
                    return true;
                }
            });
        } else {
            Student student = studentData.get(position);

            if (student != null) {
                viewHolder.studentCardColor.setBackgroundColor(student.getColor());
                setStudentName(viewHolder.studentCardInitials, student);
                viewHolder.studentCardDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        studentData.remove(position);
                        classData.get(position - 1).removeStudent(position);
                        rippleDelete();
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, studentData.size());
                    }
                });
                viewHolder.studentCardAbsent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //May someday break something... Oh whale.
                        //generateGroups(2);
                        CardView card = (CardView) v.getParent().getParent();

                        if (card.getAlpha() != .2f) {
                            makeAbsent(true, position, viewHolder);
                        } else {
                            makeAbsent(false, position, viewHolder);
                        }
                    }
                });
                if (studentData.get(position).getAbsent()) {
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            makeAbsent(true, position, viewHolder);

                            handler.postDelayed(this, 20);
                            handler.removeCallbacks(this);
                        }
                    }, 20); //Hold up 20ms to finish drawing
                }
            }
        }
    }

    public void rippleDelete() {
        HashMap<Integer, Student> newMap = new HashMap<>();

        for (int i = 0; i <= studentData.size(); i++) {
            if (studentData.get(i) != null) {
                newMap.put(newMap.size(), studentData.get(i));
            }
        }
        studentData = newMap;
    }

    public void setStudentName(TextView name, Student student) {
        SharedPreferences prefs = context.getSharedPreferences("shared_preferences", 0);
        boolean fullName = prefs.getBoolean(Settings.FN, false);

        if (fullName) {
            name.setTextSize(name.getResources().getDimension(R.dimen.student_card_text_size_name));
            name.setMaxLines(3);
            name.setText(Html.fromHtml(student.getName()));
        } else {
            name.setText(student.getInitials());
        }
    }

    public void makeAbsent(boolean isAbsent, final int position, final ViewHolder viewHolder) {
        if (isAbsent) {
            /*
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Student tempStud = studentData.get(position);
                    studentData.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, classData.size());
                    studentData.add(tempStud);
                    notifyItemInserted(studentData.size());

                    handler.postDelayed(this, 0);

                    viewHolder.studentCard.setAlpha(.2f);
                    //studentData.get(i).setAbsent(true);
                    viewHolder.studentCardDelete.setClickable(false);

                    handler.removeCallbacks(this);
                }
            }, 250);
            */
            viewHolder.studentCard.setAlpha(.2f);
            studentData.get(position).setAbsent(true);
            viewHolder.studentCardDelete.setClickable(false);

        } else {
            /*
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Student tempStud = studentData.get(position);
                    studentData.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, classData.size());
                    studentData.add(0, tempStud);
                    notifyItemInserted(0);

                    handler.postDelayed(this, 0);
                }
            }, 250);
            */
            viewHolder.studentCard.setAlpha(1f);
            studentData.get(position).setAbsent(false);
            viewHolder.studentCardDelete.setClickable(true);
        }
    }

    @Override
    public int getItemCount() {
        if (isMain) {
            return classData.size();
        } else {
            return studentData.size();
        }
    }

    public void addClassesToDrawer() {
        for (int ix = 0; ix < classData.size(); ix++) {
            final int finalI = ix;
            navigationDrawer.addItem(NavigationDrawer.PrimaryDrawerItem, classData.get(ix).getCardName(),
                    context.getResources().getDrawable(R.drawable.ic_class), -1,
                    (new Drawer.OnDrawerItemClickListener() {
                        @Override
                        public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                            Main.switchActivities(activity, "ClassRoster", finalI);
                            return false;
                        }
                    }));
        }
    }

    public void removeClass(int whichClass) {
        classData.remove(whichClass);
        notifyItemRemoved(whichClass);
        notifyItemRangeChanged(whichClass, classData.size());

        File dir = new File(context.getFilesDir().getParent() + "/shared_prefs/");

        SharedPreferences prefs = context.getSharedPreferences("shared_preferences", 0);
        SharedPreferences.Editor editor = prefs.edit();

        int numberOfClasses = prefs.getInt("numberOfClasses", 0);

        editor.remove("numberOfClasses");
        editor.putInt("numberOfClasses", numberOfClasses - 1);
        editor.apply();

        new File(dir, "class" + whichClass + ".xml").delete(); // Is this _perjury_? /s

        while (true) {
            if (new File(dir, "class" + (whichClass + 1) + ".xml").exists()) {
                new File(dir, "class" + (whichClass + 1) + ".xml").renameTo(
                        new File(dir, "class" + (whichClass) + ".xml"));
                whichClass++;
            } else {
                break;
            }
        }

        if (getItemCount() == 0) {
            ClassInfo emptyClass = new ClassInfo(-1, context);
            emptyClass.setCardName("No Classes!")
                    .setCardStudentCount("CREATE A CLASS TO GET STARTED")
                    .setCardColor(generateColor());

            classData.add(emptyClass);
            notifyItemInserted(0);
        }
    }

    public String getInitials(int key) {
        if (thisClass.get(key) != null) {
            String name = thisClass.get(key).replaceAll("[.,]", "");
            String initials = "";

            for (String s : name.split(" ")) {
                initials += s.charAt(0);
            }

            return initials;
        } else {
            return null;
        }
    }

    public String getName(int key) {
        if (thisClass.get(key) != null) {
            String name = thisClass.get(key);
            String formattedName = "";

            for (String s : name.split(" ")) {
                formattedName += s.charAt(0);

                for (int i = 1; i < s.length(); i++) {
                    formattedName += "<small>" + s.charAt(i) + "</small>";
                }

                formattedName += " ";
            }

            return formattedName;
        } else {
            return null;
        }
    }

    public int generateColor() {
        Random rand = new Random();
        return Color.argb(255,              //Opacity
                rand.nextInt(156) + 100,    //R
                rand.nextInt(156) + 100,    //G
                rand.nextInt(156) + 100);   //B
    }

    public void generateGroups(int nGroups) {
        HashMap<Integer, HashMap> groups = new HashMap<>();
        ArrayList<Integer> numbers = new ArrayList<>();
        int nStudents = studentData.size();
        int studentsPerGroup = (nStudents / nGroups);
        int counter = 1;
        int studentCounter = 0;

        for (int i = 0; i < nStudents; i++) {
            numbers.add(i);
        }

        // Even if the groups are uneven, this is our starting point
        for (int i = nGroups; i > 0; i--) {
            for (int ii = 0; ii < studentsPerGroup; ii++) {
                HashMap<Integer, Student> newGroup = new HashMap<>();
                int random = numbers.get(new Random().nextInt(numbers.size()));

                try {
                    newGroup.put(random, studentData.get(random));
                    numbers.remove(random);
                } catch (Exception e) {
                    while (true) {
                        int nRandom = numbers.get(new Random().nextInt(numbers.size()));
                        try {
                            newGroup.put(nRandom, studentData.get(nRandom));
                            numbers.remove(nRandom);
                            break;
                        } catch (Exception ex) { }
                    }
                }
            }
        }

        /* Handle uneven groups
        if (nStudents % nGroups != 0) {
            int studentsLeftOver = (students % nGroups);

            while (studentsLeftOver > 0) {
                groups.put(counter, (studentsPerGroup + 1));
                studentsLeftOver--;
                counter++;
            }
        }
        */
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public CardView studentCard;
        public RelativeLayout studentCardColor;
        public TextView studentCardInitials;
        public ImageView studentCardDelete, studentCardAbsent;
        public LinearLayout studentCardLayout;

        public TextView classCardTitle, classCardStudentCount;
        public ImageView classCardColor;
        public Toolbar classCardToolbar;
        public CardView classCard;

        public ViewHolder(View itemView) {
            super(itemView);

            if (isMain) {
                this.classCard = (CardView) itemView.findViewById(R.id.class_card);
                context = this.classCard.getContext();
                this.classCardTitle = (TextView) itemView.findViewById(R.id.class_card_title);
                this.classCardStudentCount = (TextView) itemView.findViewById(R.id.class_card_student_count);
                this.classCardColor = (ImageView) itemView.findViewById(R.id.class_card_color);
                this.classCardToolbar = (Toolbar) itemView.findViewById(R.id.class_card_toolbar);
            } else {
                this.studentCardColor = (RelativeLayout) itemView.findViewById(R.id.card_layout);
                this.studentCardInitials = (TextView) itemView.findViewById(R.id.student_name);
                this.studentCardDelete = (ImageView) itemView.findViewById(R.id.student_delete);
                this.studentCardAbsent = (ImageView) itemView.findViewById(R.id.student_absent);
                this.studentCard = (CardView) itemView.findViewById(R.id.student_card);
                this.studentCardLayout = (LinearLayout) itemView.findViewById(R.id.student_card_linlayout);
            }
        }
    }
}