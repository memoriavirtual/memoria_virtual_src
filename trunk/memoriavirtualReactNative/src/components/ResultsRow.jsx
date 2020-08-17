import React from 'react';
import {
  StyleSheet,
  View,
} from 'react-native';

import resultsStyles from '../styles/results';

import CustomButton from './CustomButton';

const styles = StyleSheet.create({
  ...resultsStyles(),
});

const ResultsRow = ({ buttonOne, buttonTwo }) => (
  <View style={styles.resultsRowContainer}>
    {
      buttonOne
        ? <CustomButton
          buttonLabel={buttonOne.label}
          action={buttonOne.onPress}
          customContainerStyle={styles.customButtonStyle}
          customLabelStyle={styles.customButtonText}
        />
        : null
    }
    {
      buttonTwo
        ? <>
          <View style={styles.resultsRowSpacer} />
          <CustomButton
            buttonLabel={buttonTwo.label}
            action={buttonTwo.onPress}
            customContainerStyle={styles.customButtonStyle}
            customLabelStyle={styles.customButtonText}
          />
        </>
        : null
    }
  </View>
);

export default ResultsRow;
