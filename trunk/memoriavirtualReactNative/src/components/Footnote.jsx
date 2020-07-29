import React from 'react';
import { Text, StyleSheet, View } from 'react-native';
import { FOOTNOTE_FIRST, FOOTNOTE_SECOND } from '../constants/general';

import footnoteStyles from '../styles/footnote';

const styles = StyleSheet.create({
  ...footnoteStyles(),
});

const Footnote = () => (
  <View style={styles.footnoteContainer}>
    <Text style={styles.footnoteText}>{FOOTNOTE_FIRST}{FOOTNOTE_SECOND}</Text>
  </View>
);

export default Footnote;