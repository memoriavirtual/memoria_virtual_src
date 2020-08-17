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

  const buttonSource = {
    label: translate('RESULTS_SOURCE'),
    onPress: () => navigation.navigate('Source'),
  };

  const buttonSubjectDescriptors = {
    label: translate('RESULTS_DESCRIPTORS'),
    onPress: () => navigation.navigate('SubjectsDescriptors'),
  };

  const buttonInformationSource = {
    label: translate('RESULTS_INFO_SOURCE'),
    onPress: () => navigation.navigate('InformationSource'),
  };

  const buttonResearchers = {
    label: translate('RESULTS_RESEARCHERS'),
    onPress: () => navigation.navigate('Researchers'),
  };

  const buttonRelatedGoods = {
    label: translate('RESULTS_RELATED_GOODS'),
    onPress: () => navigation.navigate('RelatedGoods'),
  };

  const buttonMultimidia = {
    label: translate('RESULTS_MULTIMIDIA'),
    onPress: () => navigation.navigate('Multimidia'),
  };

  const buttonReview = {
    label: translate('RESULTS_REVIEWS'),
    onPress: () => navigation.navigate('Reviews'),
  };
  /* END - BUTTONS */

  return (
    <View style={styles.resultsContainer}>
      <Title titleText="Sibipiruna" />
      <View style={styles.buttonsRowContainer}>
        <ResultsRow buttonOne={buttonGeneralInfo} buttonTwo={buttonAuthorship} />
        <ResultsRow buttonOne={buttonProduction} buttonTwo={buttonDescription} />
        <ResultsRow buttonOne={buttonState} buttonTwo={buttonDisponibility} />
        <ResultsRow buttonOne={buttonSource} buttonTwo={buttonSubjectDescriptors} />
        <ResultsRow buttonOne={buttonInformationSource} buttonTwo={buttonResearchers} />
        <ResultsRow buttonOne={buttonRelatedGoods} buttonTwo={buttonMultimidia} />
        <ResultsRow buttonOne={buttonReview} />
      </View>
      <Footnote />
    </View>
  );
};

ResultsScreen.navigationOptions = () => ({
  headerTitle: 'Resultados',
});

export default ResultsScreen;
