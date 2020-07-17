import React from 'react';
import {
  StyleSheet,
  View,
  Text,
} from 'react-native';

import Title from '../components/Title';
import resultsStyles from '../styles/results';

const styles = StyleSheet.create({
  ...resultsStyles(),
});

const ResultsScreen = () => {
  return (
    <View style={styles.resultsContainer}>
      <Title />
    </View>
  );
};

ResultsScreen.navigationOptions = () => ({
  headerTitle: 'Resultados',
});

export default ResultsScreen;