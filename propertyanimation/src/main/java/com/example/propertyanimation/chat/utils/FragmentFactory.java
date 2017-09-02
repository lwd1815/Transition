package com.example.propertyanimation.chat.utils;

import com.example.propertyanimation.chat.base.BaseFragment;
import com.example.propertyanimation.chat.fragment.ContactsFragment;
import com.example.propertyanimation.chat.fragment.ConversationFragment;
import com.example.propertyanimation.chat.fragment.NewsFragment;

/**
 * Created by huang on 2017/2/24.
 */

public class FragmentFactory {
    private static ContactsFragment contactsFragment = null;
    private static ConversationFragment conversationFragment = null;
    private static NewsFragment newsFragment = null;

    private static BaseFragment getNewsFragment() {
        if(newsFragment==null){
            newsFragment = new NewsFragment();
        }
        return newsFragment;
    }
    private static BaseFragment getConversationFragment() {
        if(conversationFragment==null){
            conversationFragment = new ConversationFragment();
        }
        return conversationFragment;
    }
    private static BaseFragment getContactsFragment() {
        if(contactsFragment==null){
            contactsFragment = new ContactsFragment();
        }
        return contactsFragment;
    }

    public static BaseFragment getFragment(int index){
        switch (index) {
            case 0:
                return getConversationFragment();
            case 1:
                return getContactsFragment();
            case 2:
                return getNewsFragment();
            default:
                return  null;
        }
    }
}
