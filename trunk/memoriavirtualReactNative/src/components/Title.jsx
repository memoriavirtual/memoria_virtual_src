import React from 'react';
import { StyleSheet, View, Text } from 'react-native';
import {
  widthPercentageToDP as wp,
  heightPercentageToDP as hp,
} from 'react-native-responsive-screen';

const styles = StyleSheet.create({
  titleContainer: {
    width: wp('95%'),
    height: hp('7.81%'),
    backgroundColor: '#EFEFEF',
    alignItems: 'center',
    justifyContent: 'center',
    marginVertical: hp('3%'),
    borderRadius: 20,
    borderWidth: 0.5,
  },
  titleText: {
    fontSize: 22,
    fontWeight: 'bold',
  },
});

const Title = ({ titleText }) => (
    <View style={styles.titleContainer}>
      <Text style={styles.titleText}>{titleText}</Text>
    </View>
);

export default Title;
