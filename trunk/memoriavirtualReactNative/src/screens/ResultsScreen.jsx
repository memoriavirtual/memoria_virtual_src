import React, { useContext } from 'react';
import {
  StyleSheet,
  View,
} from 'react-native';

import Title from '../components/Title';
import Footnote from '../components/Footnote';
import ResultsRow from '../components/ResultsRow';

import resultsStyles from '../styles/results';

import { Context as I18nContext } from '../context/I18nContext';

const styles = StyleSheet.create({
  ...resultsStyles(),
});

const ResultsScreen = ({ navigation }) => {
  const { state: { translate } } = useContext(I18nContext);

  /* BUTTONS */
  const buttonGeneralInfo = {
    label: translate('RESULTS_GENERAL_INFO'),
    onPress: () => navigation.navigate('GeneralInfo'),
  };
  
  const buttonAuthorship = {
    label: translate('RESULTS_AUTHORSHIP'),
    onPress: () => navigation.navigate('Authorship'),
  };

  const buttonProduction = {
    label: translate('RESULTS_PRODUCTION'),
    onPress: () => navigation.navigate('Production'),
  };

  const buttonDescription = {
    label: translate('RESULTS_DESCRIPTION'),
    onPress: () => navigation.navigate('Description'),
  };

  const buttonState = {
    label: translate('RESULTS_STATE'),
    onPress: () => navigation.navigate('State'),
  };

  const buttonDisponibility = {
    label: translate('RESULTS_DISPONIBILITY'),
    onPress: () => navigation.navigate('Disponibility'),
  };

  /* END - BUTTONS */

  return (
    <View style={styles.resultsContainer}>
      <Title titleText="Sibipiruna" />
      <View style={styles.buttonsRowContainer}>
        <ResultsRow buttonOne={buttonGeneralInfo} buttonTwo={buttonAuthorship} />
        <ResultsRow buttonOne={buttonProduction} buttonTwo={buttonDescription} />
        <ResultsRow buttonOne={buttonState} buttonTwo={buttonDisponibility} />
      </View>
      <Footnote />
    </View>
  );
};

ResultsScreen.navigationOptions = () => ({
  headerTitle: 'Resultados',
});

export default ResultsScreen;