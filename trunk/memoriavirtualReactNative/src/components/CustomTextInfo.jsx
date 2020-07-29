import React from 'react';
import {
  View,
  StyleSheet,
  TextInput,
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
    <View
    style={styles.spacer}
    />
    <View style={styles.formInput}>
      <TextInput
        value={infoValue}
      />
    </View>

  </View>

);

export default CustomTextInfo;
