import React from 'react';
import {
  StyleSheet,
  View,
} from 'react-native';

import Title from '../components/Title';
import resultsStyles from '../styles/results';

const styles = StyleSheet.create({
  ...resultsStyles(),
});

const ResultsScreen = () => (
    <View style={styles.resultsContainer}>
      <Title titleText="Sibipiruna" />
      <View style={styles.buttonsRowContainer}>

      </View>
    </View>
);

ResultsScreen.navigationOptions = () => ({
  headerTitle: 'Resultados',
});

export default ResultsScreen;
