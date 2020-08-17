import React from 'react';
import { View, StyleSheet } from 'react-native';
import { heightPercentageToDP as hp } from 'react-native-responsive-screen';

const styles = StyleSheet.create({
  spacerContainer: {
    marginVertical: hp('2%'),
  },
});

const VerticalSpacer = () => <View style={styles.spacerContainer} />;

export default VerticalSpacer;
