import React from 'react';
import {
  View,
  StyleSheet,
  Text,
} from 'react-native';

import textInfoStyles from '../styles/customTextInfo';

const styles = StyleSheet.create({
  ...textInfoStyles(),
});

const CustomTextInfo = ({
  infoTitle,
  infoValue,
}) => (

  <View style={styles.inputs}>
    <View style={styles.formTitle}>
      <Text style={styles.infoText}>{infoTitle}</Text>
    </View>
    <View style={styles.formInput}>
      <Text>{infoValue}</Text>
    </View>

  </View>

);

export default CustomTextInfo;
