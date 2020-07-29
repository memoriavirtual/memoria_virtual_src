import React from 'react';
import {
  View,
  Text,
  StyleSheet,
  TextInput,
} from 'react-native';

import generalinfoStyles from '../styles/generalinfo';
import * as labels from '../constants/disponibility';
import CustomTextInfo from '../components/CustomTextInfo';
import Title from '../components/Title';
import Footnote from '../components/Footnote';

const styles = StyleSheet.create({
  ...generalinfoStyles(),
});

const DisponibilityScreen = () => (
    <View style={styles.container}>
      <Title titleText="Sibipiruna" />
      <View style={styles.form}>

          <CustomTextInfo
            infoTitle={labels.DISPONIBILITY}
            infoValue={labels.EMPTY_VALUE}
          />

          <CustomTextInfo
            infoTitle={labels.ACCESS_CONDITIONS}
            infoValue={labels.EMPTY_VALUE}
          />

          <CustomTextInfo
            infoTitle={labels.CONDITIONS}
            infoValue={labels.EMPTY_VALUE}
          />

          <CustomTextInfo
            infoTitle={labels.PROTECTION}
            infoValue={labels.EMPTY_VALUE}
          />

          <CustomTextInfo
            infoTitle={labels.PROTECTION_INSTITUTE}
            infoValue={labels.EMPTY_VALUE}
          />

          <CustomTextInfo
            infoTitle={labels.NUMBER_PROCESS}
            infoValue={labels.EMPTY_VALUE}
          />

          <CustomTextInfo
            infoTitle={labels.RETURN_DATE}
            infoValue={labels.EMPTY_VALUE}
          />

          <View style={styles.inputs}>

            <View style={styles.lastFormTitle}>
              <Text style={styles.formTitleText}>{labels.USE}</Text>
            </View>
            <View
            style={styles.spacer}
            />
            <View style={styles.formInput}>
              <TextInput
                value={labels.EMPTY_VALUE}
              />
            </View>

          </View>

      </View>
      <Footnote />
    </View>
);

DisponibilityScreen.navigationOptions = () => ({
  headerTitle: labels.DISPONIBILITY,
});

export default DisponibilityScreen;
