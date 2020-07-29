import React, { Component, useState } from 'react';
import {
  View,
  Text,
  Image,
  StyleSheet,
  TextInput,
  FlatList,
  TouchableOpacity,
  Switch,
} from 'react-native';
import { Icon, Divider } from 'react-native-elements';

import generalinfoStyles from '../styles/generalinfo';
import CustomTextInfo from '../components/CustomTextInfo';

import {
  widthPercentageToDP as wp,
  heightPercentageToDP as hp,
} from 'react-native-responsive-screen';

const styles = StyleSheet.create({
  ...generalinfoStyles(),
});


const footnoteFirst = 'Copyright © 2009 LABES';
const footnoteSecond = 'Laboratório de Engenharia de Software - ICMC-USP';

const GeneralInfo = () => {

  const [isSwitchOn, setIsSwitchOn] = React.useState(false);
  const onToggleSwitch = () => setIsSwitchOn(!isSwitchOn);

  const [isSwitchOnReview, setIsSwitchOnReview] = React.useState(false);
  const onToggleSwitchReview = () => setIsSwitchOnReview(!isSwitchOnReview);
  return (
    <View style={styles.container}>
      <View style = {styles.title}>
        <Text style={{fontSize:26}}>Sibipiruna</Text>
      </View>
      <View style={styles.form}>


          <View style={styles.inputs}>

            <View style={styles.formTitle}>
              <Text style={{fontSize:18}}>Externo</Text>
            </View>

            <View
            style={styles.spacer}
            />

            <View style={styles.formInput}>
              <Text style={{fontSize:18}}>{isSwitchOn ? "Sim" : "Não"}</Text>
              <Switch
                trackColor={{ false: "#767577", true: "#81b0ff" }}
                onValueChange={onToggleSwitch}
                value={isSwitchOn}
              />
            </View>

          </View>

          <View style={styles.inputs}>

            <View style={styles.formTitle}>
              <Text style={{fontSize:18}}>Revisão</Text>
            </View>
            <View
            style={styles.spacer}
            />
            <View style={styles.formInput}>
              <Text style={{fontSize:18}}>{isSwitchOnReview ? "Sim" : "Não"}</Text>
              <Switch
                trackColor={{ false: "#767577", true: "#81b0ff" }}
                onValueChange={onToggleSwitchReview}
                value={isSwitchOnReview}
              />
            </View>

          </View>

          <CustomTextInfo
            infoTitle="Tipo"
            infoValue="-"
          />

          <CustomTextInfo
            infoTitle="Nº de registro"
            infoValue="MFF-FL-323"
          />

          <CustomTextInfo
            infoTitle="Título Principal"
            infoValue="Sibipiruna"
          />

          <CustomTextInfo
            infoTitle="Complemento"
            infoValue="-"
          />

          <CustomTextInfo
            infoTitle="Coleção"
            infoValue="Fabaceae"
          />

          <CustomTextInfo
            infoTitle="Latitude"
            infoValue="-"
          />

          <View style={styles.inputs}>

            <View style={styles.lastFormTitle}>
              <Text style={{fontSize:18}}
                value="-">Longitude</Text>
            </View>
            <View
            style={styles.spacer}
            />
            <View style={styles.formInput}>
              <TextInput
              />
            </View>

          </View>

      </View>

    <Text style={styles.footnoteText}>{footnoteFirst}{'\n'}{footnoteSecond}</Text>
    </View>
  );
};

GeneralInfo.navigationOptions = () => ({
  headerTitle: 'Informações Gerais',
  headerTitleStyle: {
    color: 'white',
    fontWeight: 'bold',
    fontSize: 22,
  },
  headerStyle: {
    backgroundColor: '#4488BB',
  },
});

export default GeneralInfo;
