package com.daejong.seoulpharm.adapter;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.daejong.seoulpharm.R;
import com.daejong.seoulpharm.model.constant.ConversationConst;
import com.daejong.seoulpharm.model.TreeNode;
import com.daejong.seoulpharm.view.ConversationItemView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hyunwoo on 2016. 10. 22..
 */
public class ConversationListAdapter extends BaseAdapter {

    List<TreeNode<String>> items = new ArrayList<>();

    TreeNode<String> root = new TreeNode<String>(ConversationConst.ROOT);
    TreeNode<String> currentSymptom = root.addChild(ConversationConst.CURRENT_SYMPTOM);
    {
        TreeNode<String> head = currentSymptom.addChild(ConversationConst.D1_HEAD);
        {
            TreeNode<String> scalp = head.addChild(ConversationConst.D2_SCALP);
            {
                TreeNode<String> bleeding = scalp.addChild(ConversationConst.BLEEDING);
                TreeNode<String> bruise = scalp.addChild(ConversationConst.BRUISE);
                TreeNode<String> itching = scalp.addChild(ConversationConst.ITCHING);
                TreeNode<String> burn = scalp.addChild(ConversationConst.BURN);
                TreeNode<String> lump = scalp.addChild(ConversationConst.LUMP);
                TreeNode<String> numbness = scalp.addChild(ConversationConst.NUMBNESS);
                TreeNode<String> swelling = scalp.addChild(ConversationConst.SWELLING);
            }
            TreeNode<String> eyes = head.addChild(ConversationConst.D2_EYES);
            {
                TreeNode<String> bleeding = eyes.addChild(ConversationConst.BLEEDING);
                TreeNode<String> blind_spot = eyes.addChild(ConversationConst.BLIND_SPOT);
                TreeNode<String> blurred_vision = eyes.addChild(ConversationConst.BLURRED_VISION);
                TreeNode<String> bruise = eyes.addChild(ConversationConst.BRUISE);
                TreeNode<String> cloudy_vision = eyes.addChild(ConversationConst.CLOUDY_VISION);
                TreeNode<String> bulging_eyes = eyes.addChild(ConversationConst.BULGING_EYES);
                TreeNode<String> mucus = eyes.addChild(ConversationConst.MUCUS);
                TreeNode<String> double_vision = eyes.addChild(ConversationConst.DOUBLE_VISION);
                TreeNode<String> pus = eyes.addChild(ConversationConst.PUS);
                TreeNode<String> dry = eyes.addChild(ConversationConst.DRY);
                TreeNode<String> irritation = eyes.addChild(ConversationConst.IRRITATION);
                TreeNode<String> redness = eyes.addChild(ConversationConst.REDNESS);
                TreeNode<String> pain = eyes.addChild(ConversationConst.PAIN);
                TreeNode<String> puffy = eyes.addChild(ConversationConst.PUFFY);
                TreeNode<String> sensation_of_something_in_the_eye = eyes.addChild(ConversationConst.SENSATION_OF_SOMETHING_IN_THE_EYE);
                TreeNode<String> watery = eyes.addChild(ConversationConst.WATERY);
            }
            TreeNode<String> nose = head.addChild(ConversationConst.D2_NOSE);
            {
                TreeNode<String> bruise = nose.addChild(ConversationConst.BRUISE);
                TreeNode<String> decreased_smell = nose.addChild(ConversationConst.DECREASED_SMELL);
                TreeNode<String> difficulty_breathing = nose.addChild(ConversationConst.DIFFICULTY_BREATHING);
                TreeNode<String> pus = nose.addChild(ConversationConst.PUS);
                TreeNode<String> episodes_of_not_breathing_during_sleep = nose.addChild(ConversationConst.EPISODES_OF_NOT_BREATHING_DURING_SLEEP);
                TreeNode<String> itching = nose.addChild(ConversationConst.ITCHING);
                TreeNode<String> nasal_cogestion = nose.addChild(ConversationConst.NASAL_COGESTION);
                TreeNode<String> nosebleed = nose.addChild(ConversationConst.NOSEBLEED);
                TreeNode<String> numbness = nose.addChild(ConversationConst.NUMBNESS);
                TreeNode<String> runny_nose = nose.addChild(ConversationConst.RUNNY_NOSE);
                TreeNode<String> sneezing = nose.addChild(ConversationConst.SNEEZING);
                TreeNode<String> pain = nose.addChild(ConversationConst.PAIN);
                TreeNode<String> snoring = nose.addChild(ConversationConst.SNORING);
                TreeNode<String> swelling = nose.addChild(ConversationConst.SWELLING);
            }
            TreeNode<String> ears = head.addChild(ConversationConst.D2_EARS);
            {
                TreeNode<String> bleeding = ears.addChild(ConversationConst.BLEEDING);
                TreeNode<String> bruising = ears.addChild(ConversationConst.BRUISING);
                TreeNode<String> pus = ears.addChild(ConversationConst.PUS);
                TreeNode<String> ear_ache = ears.addChild(ConversationConst.EAR_ACHE);
                TreeNode<String> hearing_loss = ears.addChild(ConversationConst.HEARING_LOSS);
                TreeNode<String> itching = ears.addChild(ConversationConst.ITCHING);
                TreeNode<String> lump = ears.addChild(ConversationConst.LUMP);
                TreeNode<String> numbness = ears.addChild(ConversationConst.NUMBNESS);
                TreeNode<String> ringing_in_ears = ears.addChild(ConversationConst.RINGING_IN_EARS);
                TreeNode<String> sensitive_to_noise = ears.addChild(ConversationConst.SENSITIVE_TO_NOISE);
                TreeNode<String> pain = ears.addChild(ConversationConst.PAIN);
                TreeNode<String> sweling = ears.addChild(ConversationConst.SWELING);
            }
            TreeNode<String> face = head.addChild(ConversationConst.D2_FACE);
            {
                TreeNode<String> bleeding = face.addChild(ConversationConst.BLEEDING);
                TreeNode<String> bruising = face.addChild(ConversationConst.BRUISING);
                TreeNode<String> pus = face.addChild(ConversationConst.PUS);
                TreeNode<String> lump = face.addChild(ConversationConst.LUMP);
                TreeNode<String> cramp = face.addChild(ConversationConst.CRAMP);
                TreeNode<String> numbness = face.addChild(ConversationConst.NUMBNESS);
                TreeNode<String> swelling = face.addChild(ConversationConst.SWELLING);
                TreeNode<String> feverish = face.addChild(ConversationConst.FEVERISH);
                TreeNode<String> pain = face.addChild(ConversationConst.PAIN);
            }
            TreeNode<String> mouth = head.addChild(ConversationConst.D2_MOUTH);
            {
                TreeNode<String> lips = mouth.addChild(ConversationConst.LIPS);
                TreeNode<String> gums = mouth.addChild(ConversationConst.GUMS);
                TreeNode<String> inside_mouth = mouth.addChild(ConversationConst.INSIDE_MOUTH);
                TreeNode<String> teeth = mouth.addChild(ConversationConst.TEETH);
                TreeNode<String> tongue = mouth.addChild(ConversationConst.TONGUE);
                //
                TreeNode<String> belching = mouth.addChild(ConversationConst.BELCHING);
                TreeNode<String> bleeding = mouth.addChild(ConversationConst.BLEEDING);
                TreeNode<String> bleeding_gums = mouth.addChild(ConversationConst.BLEEDING_GUMS);
                TreeNode<String> bruising = mouth.addChild(ConversationConst.BRUISING);
                TreeNode<String> choke = mouth.addChild(ConversationConst.CHOKE);
                TreeNode<String> colored_sputum = mouth.addChild(ConversationConst.COLORED_SPUTUM);
                TreeNode<String> sputum = mouth.addChild(ConversationConst.SPUTUM);
                TreeNode<String> cough = mouth.addChild(ConversationConst.COUGH);
                TreeNode<String> difficulty_opening_mouth = mouth.addChild(ConversationConst.DIFFICULTY_OPENING_MOUTH);
                TreeNode<String> difficulty_swallowing = mouth.addChild(ConversationConst.DIFFICULTY_SWALLOWING);
                TreeNode<String> difficulty_talking = mouth.addChild(ConversationConst.DIFFICULTY_TALKING);
                TreeNode<String> pus = mouth.addChild(ConversationConst.PUS);
                TreeNode<String> excessive_fluid = mouth.addChild(ConversationConst.EXCESSIVE_FLUID);
                TreeNode<String> drooling = mouth.addChild(ConversationConst.DROOLING);
                TreeNode<String> gagging = mouth.addChild(ConversationConst.GAGGING);
                TreeNode<String> grinding_teeth = mouth.addChild(ConversationConst.GRINDING_TEETH);
                TreeNode<String> sore_gum = mouth.addChild(ConversationConst.SORE_GUM);
                TreeNode<String> involuntary_movement = mouth.addChild(ConversationConst.INVOLUNTARY_MOVEMENT);
                TreeNode<String> itching = mouth.addChild(ConversationConst.ITCHING);
                TreeNode<String> jaw_locking = mouth.addChild(ConversationConst.JAW_LOCKING);
                TreeNode<String> metallic_taste = mouth.addChild(ConversationConst.METALLIC_TASTE);
                TreeNode<String> numbness = mouth.addChild(ConversationConst.NUMBNESS);
                TreeNode<String> pain = mouth.addChild(ConversationConst.PAIN);
                TreeNode<String> regurgittion_of_food_or_liquids = mouth.addChild(ConversationConst.REGURGITTION_OF_FOOD_OR_LIQUIDS);
                TreeNode<String> swelling = mouth.addChild(ConversationConst.SWELLING);
            }
            TreeNode<String> chin = head.addChild(ConversationConst.D2_CHIN);
            {
                TreeNode<String> bleeding = scalp.addChild(ConversationConst.BLEEDING);
                TreeNode<String> broken_bone = scalp.addChild(ConversationConst.BROKEN_BONE);
                TreeNode<String> bruising = scalp.addChild(ConversationConst.BRUISING);
                TreeNode<String> difficulty_moving_joint = scalp.addChild(ConversationConst.DIFFICULTY_MOVING_JOINT);
                TreeNode<String> difficulty_opening_mouth = scalp.addChild(ConversationConst.DIFFICULTY_OPENING_MOUTH);
                TreeNode<String> pus = scalp.addChild(ConversationConst.PUS);
                TreeNode<String> swelling = scalp.addChild(ConversationConst.SWELLING);
                TreeNode<String> jaw_locking = scalp.addChild(ConversationConst.JAW_LOCKING);
                TreeNode<String> lump = scalp.addChild(ConversationConst.LUMP);
                TreeNode<String> numbness = scalp.addChild(ConversationConst.NUMBNESS);
                TreeNode<String> pain = scalp.addChild(ConversationConst.PAIN);
                TreeNode<String> stiffness_or_decreased_movement = scalp.addChild(ConversationConst.STIFFNESS_OR_DECREASED_MOVEMENT);
            }
        }
        TreeNode<String> neck = currentSymptom.addChild(ConversationConst.D1_NECK);
        {
            TreeNode<String> front = neck.addChild(ConversationConst.FRONT);
            {
                TreeNode<String> bleeding = front.addChild(ConversationConst.BLEEDING);
                TreeNode<String> broken_bone = front.addChild(ConversationConst.BROKEN_BONE);
                TreeNode<String> bruising = front.addChild(ConversationConst.BRUISING);
                TreeNode<String> pus = front.addChild(ConversationConst.PUS);
                TreeNode<String> swollen_glands = front.addChild(ConversationConst.SWOLLEN_GLANDS);
                TreeNode<String> involuntary_head_turning_or_twisting = front.addChild(ConversationConst.INVOLUNTARY_HEAD_TURNING_OR_TWISTING);
                TreeNode<String> joint_ache = front.addChild(ConversationConst.JOINT_ACHE);
                TreeNode<String> lump = front.addChild(ConversationConst.LUMP);
                TreeNode<String> cramp = front.addChild(ConversationConst.CRAMP);
                TreeNode<String> numbness = front.addChild(ConversationConst.NUMBNESS);
                TreeNode<String> pain = front.addChild(ConversationConst.PAIN);
                TreeNode<String> stiffness = front.addChild(ConversationConst.STIFFNESS);
            }
            TreeNode<String> back = neck.addChild(ConversationConst.BACK);
            {
                TreeNode<String> bleeding = back.addChild(ConversationConst.BLEEDING);
                TreeNode<String> broken_bone = back.addChild(ConversationConst.BROKEN_BONE);
                TreeNode<String> bruising = back.addChild(ConversationConst.BRUISING);
                TreeNode<String> pus = back.addChild(ConversationConst.PUS);
                TreeNode<String> swollen_glands = back.addChild(ConversationConst.SWOLLEN_GLANDS);
                TreeNode<String> involuntary_head_turning_or_twisting = back.addChild(ConversationConst.INVOLUNTARY_HEAD_TURNING_OR_TWISTING);
                TreeNode<String> joint_ache = back.addChild(ConversationConst.JOINT_ACHE);
                TreeNode<String> lump = back.addChild(ConversationConst.LUMP);
                TreeNode<String> cramp = back.addChild(ConversationConst.CRAMP);
                TreeNode<String> numbness = back.addChild(ConversationConst.NUMBNESS);
                TreeNode<String> pain = back.addChild(ConversationConst.PAIN);
                TreeNode<String> stiffness = back.addChild(ConversationConst.STIFFNESS);
            }
            TreeNode<String> inner = neck.addChild(ConversationConst.INNER);
            {
                TreeNode<String> bleeding = inner.addChild(ConversationConst.BLEEDING);
                TreeNode<String> broken_bone = inner.addChild(ConversationConst.BROKEN_BONE);
                TreeNode<String> bruising = inner.addChild(ConversationConst.BRUISING);
                TreeNode<String> pus = inner.addChild(ConversationConst.PUS);
                TreeNode<String> swollen_glands = inner.addChild(ConversationConst.SWOLLEN_GLANDS);
                TreeNode<String> involuntary_head_turning_or_twisting = inner.addChild(ConversationConst.INVOLUNTARY_HEAD_TURNING_OR_TWISTING);
                TreeNode<String> joint_ache = inner.addChild(ConversationConst.JOINT_ACHE);
                TreeNode<String> lump = inner.addChild(ConversationConst.LUMP);
                TreeNode<String> cramp = inner.addChild(ConversationConst.CRAMP);
                TreeNode<String> numbness = inner.addChild(ConversationConst.NUMBNESS);
                TreeNode<String> pain = inner.addChild(ConversationConst.PAIN);
                TreeNode<String> stiffness = inner.addChild(ConversationConst.STIFFNESS);
            }
        }
        TreeNode<String> arm = currentSymptom.addChild(ConversationConst.D1_ARM);
        {
            TreeNode<String> shoulder = arm.addChild(ConversationConst.D2_SHOULDER);
            {
                TreeNode<String> bleeding = shoulder.addChild(ConversationConst.BLEEDING);
                TreeNode<String> bruising = shoulder.addChild(ConversationConst.BRUISING);
                TreeNode<String> difficulty_moving_joint = shoulder.addChild(ConversationConst.DIFFICULTY_MOVING_JOINT);
                TreeNode<String> pus = shoulder.addChild(ConversationConst.PUS);
                TreeNode<String> inability_to_move = shoulder.addChild(ConversationConst.INABILITY_TO_MOVE);
                TreeNode<String> joint_ache = shoulder.addChild(ConversationConst.JOINT_ACHE);
                TreeNode<String> lump = shoulder.addChild(ConversationConst.LUMP);
                TreeNode<String> numbness = shoulder.addChild(ConversationConst.NUMBNESS);
                TreeNode<String> popping_or_snapping_sound_from_joint = shoulder.addChild(ConversationConst.POPPING_OR_SNAPPING_SOUND_FROM_JOINT);
                TreeNode<String> swelling = shoulder.addChild(ConversationConst.SWELLING);
                TreeNode<String> broken_bone = shoulder.addChild(ConversationConst.BROKEN_BONE);
                TreeNode<String> stiffness = shoulder.addChild(ConversationConst.STIFFNESS);
            }
            TreeNode<String> armpit = arm.addChild(ConversationConst.D2_ARMPIT);
            {
                TreeNode<String> bleeding = armpit.addChild(ConversationConst.BLEEDING);
                TreeNode<String> bruising = armpit.addChild(ConversationConst.BRUISING);
                TreeNode<String> pus = armpit.addChild(ConversationConst.PUS);
                TreeNode<String> lump = armpit.addChild(ConversationConst.LUMP);
                TreeNode<String> numbness = armpit.addChild(ConversationConst.NUMBNESS);
                TreeNode<String> popping_or_snapping_sound_from_joint = armpit.addChild(ConversationConst.POPPING_OR_SNAPPING_SOUND_FROM_JOINT);
                TreeNode<String> swelling = armpit.addChild(ConversationConst.SWELLING);
                TreeNode<String> stiffness = armpit.addChild(ConversationConst.STIFFNESS);
                TreeNode<String> excessive_sweating = armpit.addChild(ConversationConst.EXCESSIVE_SWEATING);
            }
            TreeNode<String> upper_arm = arm.addChild(ConversationConst.D2_UPPER_ARM);
            {
                TreeNode<String> tricep = upper_arm.addChild(ConversationConst.TRICEP);
                TreeNode<String> bicep = upper_arm.addChild(ConversationConst.BICEP);
                TreeNode<String> elbow = upper_arm.addChild(ConversationConst.ELBOW);
                TreeNode<String> wrist = upper_arm.addChild(ConversationConst.WRIST);
                TreeNode<String> fore_arm = upper_arm.addChild(ConversationConst.FORE_ARM);
                //
                TreeNode<String> bleeding = upper_arm.addChild(ConversationConst.BLEEDING);
                TreeNode<String> bruising = upper_arm.addChild(ConversationConst.BRUISING);
                TreeNode<String> pus = upper_arm.addChild(ConversationConst.PUS);
                TreeNode<String> lump = upper_arm.addChild(ConversationConst.LUMP);
                TreeNode<String> numbness = upper_arm.addChild(ConversationConst.NUMBNESS);
                TreeNode<String> popping_or_snapping_sound_from_joint = upper_arm.addChild(ConversationConst.POPPING_OR_SNAPPING_SOUND_FROM_JOINT);
                TreeNode<String> swelling = upper_arm.addChild(ConversationConst.SWELLING);
                TreeNode<String> stiffness = upper_arm.addChild(ConversationConst.STIFFNESS);
                TreeNode<String> broken_bone = upper_arm.addChild(ConversationConst.BROKEN_BONE);
                TreeNode<String> muscle_cramps_or_spasms = upper_arm.addChild(ConversationConst.MUSCLE_CRAMPS_OR_SPASMS);
            }
            TreeNode<String> hand = arm.addChild(ConversationConst.D2_HAND);
            {
                TreeNode<String> palm = hand.addChild(ConversationConst.PALM);
                TreeNode<String> fingers = hand.addChild(ConversationConst.FINGERS);
                TreeNode<String> back_of_hand = hand.addChild(ConversationConst.BACK_OF_HAND);
                TreeNode<String> nails = hand.addChild(ConversationConst.NAILS);
                //
                TreeNode<String> bleeding = hand.addChild(ConversationConst.BLEEDING);
                TreeNode<String> bruising = hand.addChild(ConversationConst.BRUISING);
                TreeNode<String> pus = hand.addChild(ConversationConst.PUS);
                TreeNode<String> lump = hand.addChild(ConversationConst.LUMP);
                TreeNode<String> numbness = hand.addChild(ConversationConst.NUMBNESS);
                TreeNode<String> popping_or_snapping_sound_from_joint = hand.addChild(ConversationConst.POPPING_OR_SNAPPING_SOUND_FROM_JOINT);
                TreeNode<String> swelling = hand.addChild(ConversationConst.SWELLING);
                TreeNode<String> stiffness = hand.addChild(ConversationConst.STIFFNESS);
                TreeNode<String> cold_hands = hand.addChild(ConversationConst.COLD_HANDS);
                TreeNode<String> color_change = hand.addChild(ConversationConst.COLOR_CHANGE);
                TreeNode<String> broken_bone = hand.addChild(ConversationConst.BROKEN_BONE);
                TreeNode<String> involuntary_movement = hand.addChild(ConversationConst.INVOLUNTARY_MOVEMENT);
                TreeNode<String> cramps_or_spasms = hand.addChild(ConversationConst.CRAMPS_OR_SPASMS);
                TreeNode<String> shaking_hands_of_tremor = hand.addChild(ConversationConst.SHAKING_HANDS_OF_TREMOR);
                TreeNode<String> joint_pain = hand.addChild(ConversationConst.JOINT_PAIN);
                TreeNode<String> inability_to_move = hand.addChild(ConversationConst.INABILITY_TO_MOVE);
                TreeNode<String> muscle_cramps_or_spasms = hand.addChild(ConversationConst.MUSCLE_CRAMPS_OR_SPASMS);
            }
        }
        TreeNode<String> chest = currentSymptom.addChild(ConversationConst.D1_CHEST);
        {
            TreeNode<String> breast = chest.addChild(ConversationConst.BREAST);
            TreeNode<String> sternum = chest.addChild(ConversationConst.STERNUM);
            TreeNode<String> spine = chest.addChild(ConversationConst.SPINE);
            TreeNode<String> back = chest.addChild(ConversationConst.BACK);
            TreeNode<String> lateral_chest = chest.addChild(ConversationConst.LATERAL_CHEST);
            //
            TreeNode<String> bleeding = chest.addChild(ConversationConst.BLEEDING);
            TreeNode<String> bruising = chest.addChild(ConversationConst.BRUISING);
            TreeNode<String> pus = chest.addChild(ConversationConst.PUS);
            TreeNode<String> lump = chest.addChild(ConversationConst.LUMP);
            TreeNode<String> numbness = chest.addChild(ConversationConst.NUMBNESS);
            TreeNode<String> popping_or_snapping_sound_from_joint = chest.addChild(ConversationConst.POPPING_OR_SNAPPING_SOUND_FROM_JOINT);
            TreeNode<String> swelling = chest.addChild(ConversationConst.SWELLING);
            TreeNode<String> stiffness = chest.addChild(ConversationConst.STIFFNESS);
            TreeNode<String> broken_bone = chest.addChild(ConversationConst.BROKEN_BONE);
            TreeNode<String> irregular_heartbeat = chest.addChild(ConversationConst.IRREGULAR_HEARTBEAT);
            TreeNode<String> heartburn = chest.addChild(ConversationConst.HEARTBURN);
            TreeNode<String> difficulty_swallowing = chest.addChild(ConversationConst.DIFFICULTY_SWALLOWING);
            TreeNode<String> hyperventilation = chest.addChild(ConversationConst.HYPERVENTILATION);
            TreeNode<String> cramps_or_spasms = chest.addChild(ConversationConst.CRAMPS_OR_SPASMS);
            TreeNode<String> pressure_or_heaviness = chest.addChild(ConversationConst.PRESSURE_OR_HEAVINESS);
            TreeNode<String> shortness_of_breath = chest.addChild(ConversationConst.SHORTNESS_OF_BREATH);
            TreeNode<String> slow_heart_rate = chest.addChild(ConversationConst.SLOW_HEART_RATE);
            TreeNode<String> wheezing = chest.addChild(ConversationConst.WHEEZING);
            TreeNode<String> labored_breathing = chest.addChild(ConversationConst.LABORED_BREATHING);
            TreeNode<String> curved_spine = chest.addChild(ConversationConst.CURVED_SPINE);
            TreeNode<String> difficulty_walking = chest.addChild(ConversationConst.DIFFICULTY_WALKING);
            TreeNode<String> muscle_cramps_or_spasms = chest.addChild(ConversationConst.MUSCLE_CRAMPS_OR_SPASMS);
        }
        TreeNode<String> abdomen = currentSymptom.addChild(ConversationConst.D1_ABDOMEN);
        {
            TreeNode<String> breast = abdomen.addChild(ConversationConst.BREAST);
            TreeNode<String> sternum = abdomen.addChild(ConversationConst.STERNUM);
            //
            TreeNode<String> bleeding = abdomen.addChild(ConversationConst.BLEEDING);
            TreeNode<String> bruising = abdomen.addChild(ConversationConst.BRUISING);
            TreeNode<String> pus = abdomen.addChild(ConversationConst.PUS);
            TreeNode<String> lump = abdomen.addChild(ConversationConst.LUMP);
            TreeNode<String> numbness = abdomen.addChild(ConversationConst.NUMBNESS);
            TreeNode<String> popping_or_snapping_sound_from_joint = abdomen.addChild(ConversationConst.POPPING_OR_SNAPPING_SOUND_FROM_JOINT);
            TreeNode<String> swelling = abdomen.addChild(ConversationConst.SWELLING);
            TreeNode<String> stiffness = abdomen.addChild(ConversationConst.STIFFNESS);
            TreeNode<String> broken_bone = abdomen.addChild(ConversationConst.BROKEN_BONE);
            TreeNode<String> vomit = abdomen.addChild(ConversationConst.VOMIT);
            TreeNode<String> bulging_veins = abdomen.addChild(ConversationConst.BULGING_VEINS);
            TreeNode<String> constipation = abdomen.addChild(ConversationConst.CONSTIPATION);
            TreeNode<String> disintended_stomach = abdomen.addChild(ConversationConst.DISINTENDED_STOMACH);
            TreeNode<String> nausea = abdomen.addChild(ConversationConst.NAUSEA);
            TreeNode<String> stomach_cramps = abdomen.addChild(ConversationConst.STOMACH_CRAMPS);
            TreeNode<String> upset_stomach = abdomen.addChild(ConversationConst.UPSET_STOMACH);
            TreeNode<String> pressure = abdomen.addChild(ConversationConst.PRESSURE);
            TreeNode<String> painful_bowel_movements = abdomen.addChild(ConversationConst.PAINFUL_BOWEL_MOVEMENTS);
            TreeNode<String> gaseous = abdomen.addChild(ConversationConst.GASEOUS);
            TreeNode<String> diarrhea = abdomen.addChild(ConversationConst.DIARRHEA);
            TreeNode<String> muscle_cramps_or_spasms = abdomen.addChild(ConversationConst.MUSCLE_CRAMPS_OR_SPASMS);
        }
        TreeNode<String> pelvis = currentSymptom.addChild(ConversationConst.D1_PELVIS);
        {
            TreeNode<String> hip = pelvis.addChild(ConversationConst.HIP);
            TreeNode<String> pelvis1 = pelvis.addChild(ConversationConst.PELVIS);
            //
            TreeNode<String> buttocks = pelvis.addChild(ConversationConst.BUTTOCKS);
            TreeNode<String> groin = pelvis.addChild(ConversationConst.GROIN);
            TreeNode<String> genitals = pelvis.addChild(ConversationConst.GENITALS);
            TreeNode<String> sternum = pelvis.addChild(ConversationConst.STERNUM);
            TreeNode<String> bleeding = pelvis.addChild(ConversationConst.BLEEDING);
            TreeNode<String> bruising = pelvis.addChild(ConversationConst.BRUISING);
            TreeNode<String> pus = pelvis.addChild(ConversationConst.PUS);
            TreeNode<String> lump = pelvis.addChild(ConversationConst.LUMP);
            TreeNode<String> numbness = pelvis.addChild(ConversationConst.NUMBNESS);
            TreeNode<String> popping_or_snapping_sound_from_joint = pelvis.addChild(ConversationConst.POPPING_OR_SNAPPING_SOUND_FROM_JOINT);
            TreeNode<String> swelling = pelvis.addChild(ConversationConst.SWELLING);
            TreeNode<String> stiffness = pelvis.addChild(ConversationConst.STIFFNESS);
            TreeNode<String> painful_bowel_movements = pelvis.addChild(ConversationConst.PAINFUL_BOWEL_MOVEMENTS);
            TreeNode<String> joint_ache = pelvis.addChild(ConversationConst.JOINT_ACHE);
            TreeNode<String> menstruational_cramp = pelvis.addChild(ConversationConst.MENSTRUATIONAL_CRAMP);
            TreeNode<String> itching = pelvis.addChild(ConversationConst.ITCHING);
            TreeNode<String> muscle_cramps_or_spasms = pelvis.addChild(ConversationConst.MUSCLE_CRAMPS_OR_SPASMS);
        }
        TreeNode<String> leg = currentSymptom.addChild(ConversationConst.D1_LEG);
        {
            TreeNode<String> upper_leg = leg.addChild(ConversationConst.D2_UPPER_LEG);
            {
                TreeNode<String> thigh = upper_leg.addChild(ConversationConst.THIGH);
                TreeNode<String> hamstring = upper_leg.addChild(ConversationConst.HAMSTRING);
                //
                TreeNode<String> bleeding = upper_leg.addChild(ConversationConst.BLEEDING);
                TreeNode<String> bruising = upper_leg.addChild(ConversationConst.BRUISING);
                TreeNode<String> pus = upper_leg.addChild(ConversationConst.PUS);
                TreeNode<String> inability_to_move_leg = upper_leg.addChild(ConversationConst.INABILITY_TO_MOVE_LEG);
                TreeNode<String> pain = upper_leg.addChild(ConversationConst.PAIN);
                TreeNode<String> lump = upper_leg.addChild(ConversationConst.LUMP);
                TreeNode<String> numbness = upper_leg.addChild(ConversationConst.NUMBNESS);
                TreeNode<String> muscle_cramps_or_spasms = upper_leg.addChild(ConversationConst.MUSCLE_CRAMPS_OR_SPASMS);
                TreeNode<String> swelling = upper_leg.addChild(ConversationConst.SWELLING);
                TreeNode<String> broken_bone = upper_leg.addChild(ConversationConst.BROKEN_BONE);
                TreeNode<String> stiffness = upper_leg.addChild(ConversationConst.STIFFNESS);
            }
            TreeNode<String> knee = leg.addChild(ConversationConst.D2_KNEE);
            {
                TreeNode<String> bleeding = knee.addChild(ConversationConst.BLEEDING);
                TreeNode<String> bruising = knee.addChild(ConversationConst.BRUISING);
                TreeNode<String> difficulty_moving_joint = knee.addChild(ConversationConst.DIFFICULTY_MOVING_JOINT);
                TreeNode<String> pus = knee.addChild(ConversationConst.PUS);
                TreeNode<String> inability_to_move = knee.addChild(ConversationConst.INABILITY_TO_MOVE);
                TreeNode<String> joint_ache = knee.addChild(ConversationConst.JOINT_ACHE);
                TreeNode<String> lump = knee.addChild(ConversationConst.LUMP);
                TreeNode<String> numbness = knee.addChild(ConversationConst.NUMBNESS);
                TreeNode<String> popping_or_snapping_sound_from_joint = knee.addChild(ConversationConst.POPPING_OR_SNAPPING_SOUND_FROM_JOINT);
                TreeNode<String> swelling = knee.addChild(ConversationConst.SWELLING);
                TreeNode<String> broken_bone = knee.addChild(ConversationConst.BROKEN_BONE);
                TreeNode<String> stiffness = knee.addChild(ConversationConst.STIFFNESS);
                TreeNode<String> unable_to_bear_weight = knee.addChild(ConversationConst.UNABLE_TO_BEAR_WEIGHT);
            }
            TreeNode<String> lower_leg = leg.addChild(ConversationConst.D2_LOWER_LEG);
            {
                TreeNode<String> shin = lower_leg.addChild(ConversationConst.SHIN);
                TreeNode<String> calf = lower_leg.addChild(ConversationConst.CALF);
                //
                TreeNode<String> bleeding = lower_leg.addChild(ConversationConst.BLEEDING);
                TreeNode<String> bruising = lower_leg.addChild(ConversationConst.BRUISING);
                TreeNode<String> pus = lower_leg.addChild(ConversationConst.PUS);
                TreeNode<String> lump = lower_leg.addChild(ConversationConst.LUMP);
                TreeNode<String> numbness = lower_leg.addChild(ConversationConst.NUMBNESS);
                TreeNode<String> bulging_eins = lower_leg.addChild(ConversationConst.BULGING_EINS);
                TreeNode<String> swelling = lower_leg.addChild(ConversationConst.SWELLING);
                TreeNode<String> stiffness = lower_leg.addChild(ConversationConst.STIFFNESS);
                TreeNode<String> pain = lower_leg.addChild(ConversationConst.PAIN);
                TreeNode<String> broken_bone = lower_leg.addChild(ConversationConst.BROKEN_BONE);
                TreeNode<String> unable_to_bear_weight = lower_leg.addChild(ConversationConst.UNABLE_TO_BEAR_WEIGHT);
                TreeNode<String> muscle_cramps_or_spasms = lower_leg.addChild(ConversationConst.MUSCLE_CRAMPS_OR_SPASMS);
            }
            TreeNode<String> ankle = leg.addChild(ConversationConst.D2_ANKLE);
            {
                TreeNode<String> bleeding = ankle.addChild(ConversationConst.BLEEDING);
                TreeNode<String> bruising = ankle.addChild(ConversationConst.BRUISING);
                TreeNode<String> difficulty_moving_joint = ankle.addChild(ConversationConst.DIFFICULTY_MOVING_JOINT);
                TreeNode<String> pus = ankle.addChild(ConversationConst.PUS);
                TreeNode<String> inability_to_move = ankle.addChild(ConversationConst.INABILITY_TO_MOVE);
                TreeNode<String> joint_ache = ankle.addChild(ConversationConst.JOINT_ACHE);
                TreeNode<String> lump = ankle.addChild(ConversationConst.LUMP);
                TreeNode<String> numbness = ankle.addChild(ConversationConst.NUMBNESS);
                TreeNode<String> popping_or_snapping_sound_from_joint = ankle.addChild(ConversationConst.POPPING_OR_SNAPPING_SOUND_FROM_JOINT);
                TreeNode<String> swelling = ankle.addChild(ConversationConst.SWELLING);
                TreeNode<String> broken_bone = ankle.addChild(ConversationConst.BROKEN_BONE);
                TreeNode<String> stiffness = ankle.addChild(ConversationConst.STIFFNESS);
                TreeNode<String> unable_to_bear_weight = ankle.addChild(ConversationConst.UNABLE_TO_BEAR_WEIGHT);
                TreeNode<String> unable_to_bend_foot_down = ankle.addChild(ConversationConst.UNABLE_TO_BEND_FOOT_DOWN);
                TreeNode<String> difficulty_walking = ankle.addChild(ConversationConst.DIFFICULTY_WALKING);
            }
            TreeNode<String> feet = leg.addChild(ConversationConst.D2_FEET);
            {
                TreeNode<String> sole = feet.addChild(ConversationConst.SOLE);
                TreeNode<String> foot = feet.addChild(ConversationConst.FOOT);
                TreeNode<String> toe = feet.addChild(ConversationConst.TOE);
                TreeNode<String> toe_nails = feet.addChild(ConversationConst.TOE_NAILS);
                //
                TreeNode<String> bleeding = leg.addChild(ConversationConst.BLEEDING);
                TreeNode<String> bruising = leg.addChild(ConversationConst.BRUISING);
                TreeNode<String> difficulty_moving_joint = leg.addChild(ConversationConst.DIFFICULTY_MOVING_JOINT);
                TreeNode<String> pus = leg.addChild(ConversationConst.PUS);
                TreeNode<String> inability_to_move = leg.addChild(ConversationConst.INABILITY_TO_MOVE);
                TreeNode<String> joint_ache = leg.addChild(ConversationConst.JOINT_ACHE);
                TreeNode<String> lump = leg.addChild(ConversationConst.LUMP);
                TreeNode<String> numbness = leg.addChild(ConversationConst.NUMBNESS);
                TreeNode<String> popping_or_snapping_sound_from_joint = leg.addChild(ConversationConst.POPPING_OR_SNAPPING_SOUND_FROM_JOINT);
                TreeNode<String> swelling = leg.addChild(ConversationConst.SWELLING);
                TreeNode<String> broken_bone = leg.addChild(ConversationConst.BROKEN_BONE);
                TreeNode<String> stiffness = leg.addChild(ConversationConst.STIFFNESS);
                TreeNode<String> unable_to_bear_weight = leg.addChild(ConversationConst.UNABLE_TO_BEAR_WEIGHT);
                TreeNode<String> unable_to_bend_foot_down = leg.addChild(ConversationConst.UNABLE_TO_BEND_FOOT_DOWN);
                TreeNode<String> difficulty_walking = leg.addChild(ConversationConst.DIFFICULTY_WALKING);
                TreeNode<String> cold_feet = leg.addChild(ConversationConst.COLD_FEET);
                TreeNode<String> color_change = leg.addChild(ConversationConst.COLOR_CHANGE);
                TreeNode<String> muscle_cramps_or_spams = leg.addChild(ConversationConst.MUSCLE_CRAMPS_OR_SPAMS);
                TreeNode<String> shuffling_gait = leg.addChild(ConversationConst.SHUFFLING_GAIT);
            }
        }
    }

    {
        TreeNode<String> chronicDisease = root.addChild(ConversationConst.CHRONIC_DISEASE);
    }


    // CURRENT NODE (현재 보여지고 있는 화면)
    private TreeNode<String> currentNode = root;

    public TreeNode<String> getCurrentNode() {
        return currentNode;
    }

    public boolean currentNodeIsRoot() {
        return (currentNode==root) ? true : false;
    }

    // 상위 계층으로
    public void currentNodeToParent() {
        currentNode = currentNode.getParent();
        showCurrentList();
    }

    // 하위 계층으로
    public void currentNodeToChild(String selectedChildName) {
        for (TreeNode<String> child : currentNode.getChildren()) {
            // 선택된 child item을 탐색
            if (child.toString().equals(selectedChildName)) {
                // 선택된 child가 마지막 노드라면
                if (child.isLastChild()) {
                    return;             // 하위 노드로 이동하지 않는다.
                }
                // 선택된 child가 마지막 노드가 아니라면
                currentNode = child;    // 현재 노드를 child 노드로 바꾸고
                showCurrentList();      // 리스트에 뿌려준다.
                return;
            }
        }
    }

    // current node의 children 리스트를 items에 담는다.
    public void showCurrentList() {
        items.clear();
        for (TreeNode<String> child : currentNode.getChildren()) {
            items.add(child);
        }
        notifyDataSetChanged();
    }

    // 선택된 노드 정보를 가져온다.
    public TreeNode<String> getSelectedNode(String selectedChildName) {
        for (TreeNode<String> child : currentNode.getChildren()) {
            if (child.toString().equals(selectedChildName)) {
                return child;
            }
        }
        return null;
    }

    // Node의 selected 상태를 바꿈
    public void setNodeSelected(int position, boolean isSelected) {
        items.get(position).setSelected(isSelected);
        notifyDataSetChanged();
    }
    // Node의 selected 상태를 반환
    public boolean getNodeSelected(int position) {
        return items.get(position).getSelected();
    }


    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ConversationItemView view;
        if (convertView != null) {
            view = (ConversationItemView) convertView;
        } else {
            view = new ConversationItemView(parent.getContext());
        }
        if (items.get(position).getSelected()) {
            view.findViewById(R.id.list_item).setBackgroundColor(parent.getContext().getResources().getColor(R.color.colorPrimary));
            Log.d("SELECTED!!!!", "POSITION"+position+"  BG"+view.getBackground());
        } else {
            view.findViewById(R.id.list_item).setBackgroundColor(parent.getContext().getResources().getColor(R.color.color_efefef));
            Log.d("????????????", "POSITION"+position+"  BG"+view.getBackground());
        }
        view.setItemText(items.get(position).toString());
        return view;
    }

}
