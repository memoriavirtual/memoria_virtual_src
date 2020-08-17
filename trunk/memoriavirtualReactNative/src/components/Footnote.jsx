import React, { useContext } from 'react';
import { Text, StyleSheet, View } from 'react-native';

import { Context as I18nContext } from '../context/I18nContext';

import footnoteStyles from '../styles/footnote';

const styles = StyleSheet.create({
  ...footnoteStyles(),
});

const Footnote = () => {
  const { state: { translate } } = useContext(I18nContext);

  return (
    <View style={styles.footnoteContainer}>
      <Text style={styles.footnoteText}>
        {translate('FOOTNOTE_FIRST')}
        {translate('FOOTNOTE_SECOND')}
      </Text>
    </View>
  );
};

export default Footnote;
