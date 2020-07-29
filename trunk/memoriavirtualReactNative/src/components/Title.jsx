import React from 'react';
import { StyleSheet, View, Text } from 'react-native';
import {
  widthPercentageToDP as wp,
  heightPercentageToDP as hp,
} from 'react-native-responsive-screen';

const styles = StyleSheet.create({
  titleContainer: {
    width: wp('92%'),
    height: hp('7.81%'),
    backgroundColor: '#EFEFEF',
    alignItems: 'center',
    justifyContent: 'center',
    marginVertical: hp('3%'),
  },
  titleText: {
    fontSize: 20,
    fontWeight: '500',
  },
});

const Title = ({ titleText }) => (
    <View style={styles.titleContainer}>
      <Text style={styles.titleText}>{titleText}</Text>
    </View>
);

export default Title;
