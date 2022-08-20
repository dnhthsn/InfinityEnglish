package com.example.infinityenglish.viewmodel;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.infinityenglish.R;
import com.example.infinityenglish.control.Repository;
import com.example.infinityenglish.control.local.SharedPreference;
import com.example.infinityenglish.control.rest.Callback;
import com.example.infinityenglish.models.Users;
import com.example.infinityenglish.util.Const;
import com.example.infinityenglish.util.Utility;
import com.example.infinityenglish.view.activity.LoginActivity;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserViewModel extends ViewModel {
    private Repository repository;
    private SharedPreference sharedPreference;
    private MutableLiveData<String> message = new MutableLiveData<>();
    private MutableLiveData<Const.State> state = new MutableLiveData<>();

    public void init(Context context) {
        this.repository = new Repository(context);
        sharedPreference = new SharedPreference(context);
    }

    public MutableLiveData<String> getMessage() {
        return message;
    }

    public MutableLiveData<Const.State> getState() {
        return state;
    }

    public void addUser(Users users, View view) {
        String name = users.getName();
        String phone = users.getPhone();
        String password = users.getPassword();
        String address = users.getAddress();
        String email = users.getEmail();
        String avatar = users.getAvatar();
        Pattern pattern = Pattern.compile(Const.Regex.emailRegex);
        Matcher matcher = pattern.matcher(email);

        if (TextUtils.isEmpty(name) || name.length() < 3) {
            message.setValue(Const.Error.name);
        } else if (TextUtils.isEmpty(phone)) {
            message.setValue(Const.Error.phone);
        } else if (TextUtils.isEmpty(password) || password.length() < 8) {
            message.setValue(Const.Error.password);
        } else if (TextUtils.isEmpty(address)) {
            message.setValue(Const.Error.address);
        } else if (TextUtils.isEmpty(email) || !matcher.matches()) {
            message.setValue(Const.Error.email);
        } else if (avatar.equals("null")) {
            message.setValue(Const.Error.avatar);
        } else {
            message.setValue(Const.Success.created);
            state.setValue(Const.State.Login);
            repository.addUser(users, view);
        }
    }

    public void updateUser(Users users) {
        String name = users.getName();
        String phone = users.getPhone();
        String password = users.getPassword();
        String address = users.getAddress();
        String email = users.getEmail();
        Pattern pattern = Pattern.compile(Const.Regex.emailRegex);
        Matcher matcher = pattern.matcher(email);

        if (TextUtils.isEmpty(name) || name.length() < 3) {
            message.setValue(Const.Error.name);
        } else if (TextUtils.isEmpty(phone)) {
            message.setValue(Const.Error.phone);
        } else if (TextUtils.isEmpty(password) || password.length() < 8) {
            message.setValue(Const.Error.password);
        } else if (TextUtils.isEmpty(address)) {
            message.setValue(Const.Error.address);
        } else if (TextUtils.isEmpty(email) || !matcher.matches()) {
            message.setValue(Const.Error.email);
        } else {
            repository.updateUser(users);
            state.setValue(Const.State.Login);
            message.setValue(Const.Success.update);
        }
    }

    public void updatePassword(Users users, String rePassword, View view) {
        String name = users.getName();
        String password = users.getPassword();
        String phone = users.getPhone();

        if (TextUtils.isEmpty(name) || name.length() < 3) {
            message.setValue(Const.Error.name);
        } else if (TextUtils.isEmpty(password) || password.length() < 8) {
            message.setValue(Const.Error.password);
        } else if (!password.equals(rePassword)) {
            message.setValue(Const.Error.notMatch);
        } else {
            message.setValue("");
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setView(LayoutInflater.from(view.getContext()).inflate(R.layout.dialog_update, null));
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });

            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Users users = new Users();
                    users.setName(name);
                    users.setPassword(password);
                    users.setPhone(phone);

                    repository.updatePassword(users, view);
                }
            });

            AlertDialog dialog = builder.create();
            dialog.getWindow().setBackgroundDrawableResource(R.drawable.round_corner_white);
            dialog.show();
        }
    }

    public Users getCurrentUser() {
        return sharedPreference.getCurrentUser();
    }

    public void saveUserAfterLogout(String name, String password) {
        sharedPreference.saveUser(name, password);
    }

    public void removeUser() {
        sharedPreference.removeUser();
    }

    public void removeCurrentUser() {
        sharedPreference.removeCurrentUser();
        state.setValue(Const.State.Start);
    }

    public void getUserAvatar(Users users, View view, CircleImageView circleImageView) {
        repository.getUserAvatar(users, view, new Callback() {
            @Override
            public void getAvatar(Bitmap bitmap) {
                super.getAvatar(bitmap);

                circleImageView.setImageBitmap(bitmap);
            }
        });
    }

    public void checkUser(Users users) {
        if (users != null) {
            String name = users.getName();
            String password = users.getPassword();
            if (TextUtils.isEmpty(name)) {
                message.setValue(Const.Error.name);
            } else if (TextUtils.isEmpty(password)) {
                message.setValue(Const.Error.password);
            } else {
                repository.getUser(new Callback() {
                    @Override
                    public void getUser(List<Users> list) {
                        super.getUser(list);
                        for (Users user : list) {
                            if (user.getName().equals(name) && user.getPassword().equals(password)) {
                                sharedPreference.saveCurrentUser(user);
                                state.setValue(Const.State.Main);
                                message.setValue("");
                                break;
                            } else {
                                state.setValue(Const.State.Start);
                                message.setValue(Const.Error.information);
                                break;
                            }
                        }
                    }
                });
            }
        } else {
            state.setValue(Const.State.Start);
        }
    }
}
