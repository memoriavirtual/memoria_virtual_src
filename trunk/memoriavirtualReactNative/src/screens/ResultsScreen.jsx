import React from 'react';
import {
  StyleSheet,
  View,
} from 'react-native';

import Title from '../components/Title';
import Footnote from '../components/Footnote';

import resultsStyles from '../styles/results';

import * as labels from '../constants/results';
import ResultsRow from '../components/ResultsRow';

const styles = StyleSheet.create({
  ...resultsStyles(),
});

const ResultsScreen = ({ navigation }) => {
  
  /* BUTTONS */
  const buttonGeneralInfo = {
    label: labels.GENERAL_INFO,
    onPress: () => navigation.navigate('GeneralInfo'),
  };
  
  const buttonAuthorship = {
    label: labels.AUTHORSHIP,
    onPress: () => navigation.navigate('Authorship'),
  };

  const buttonProduction = {
    label: labels.PRODUCTION,
    onPress: () => navigation.navigate('Production'),
  };

  /* END - BUTTONS */

  return (
    <View style={styles.resultsContainer}>
      <Title titleText="Sibipiruna" />
      <View style={styles.buttonsRowContainer}>
        <ResultsRow buttonOne={buttonGeneralInfo} buttonTwo={buttonAuthorship} />
      </View>
      <Footnote />
    </View>
  );
};

ResultsScreen.navigationOptions = () => ({
  headerTitle: 'Resultados',
});

export default ResultsScreen;
